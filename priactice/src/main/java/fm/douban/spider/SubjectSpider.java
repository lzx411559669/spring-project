package fm.douban.spider;

import com.alibaba.fastjson.JSON;
import fm.douban.model.Singer;
import fm.douban.model.Song;
import fm.douban.model.Subject;
import fm.douban.service.SingerService;
import fm.douban.service.SongService;
import fm.douban.service.SubjectService;
import fm.douban.util.HttpUtil;
import fm.douban.util.SubjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class SubjectSpider {
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private SingerService singerService;
    @Autowired
    private SongService songService;
    private HttpUtil httpUtil = new HttpUtil();
    private Map<String, String> header = httpUtil.buildHeaderData("https://fm.douban.com/", "fm.douban.com");

    //系统启动的时候自动执行爬取任务
   // @PostConstruct
    public void init() {
        doExcute();
    }
    //开始执行爬取任务
    public void doExcute() {
        //调用爬取主题数据方法
        /*getSubjectData();
        //调用爬取歌手歌曲方法
        singerService.getAll().forEach(singer -> {
            getSongDataBySingers(singer);
        });*/
        getCollection();
    }

    /**
     * 爬取主题数据并保存
     */
     //@Test
    private void getSubjectData() {
        String subjectData = httpUtil.getContent("https://fm.douban.com/j/v2/rec_channels?specific=all", header);
        System.out.println("content" + subjectData);
        Map returnData = JSON.parseObject(subjectData, Map.class);
        Map data = (Map) returnData.get("data");
        Map channels = (Map) data.get("channels");
        List<String> subjectTypes = new ArrayList<>();
        String[] strings = new String[]{"artist", "scenario", "language", "genre"};
        subjectTypes = Arrays.asList(strings);
        subjectTypes.forEach(type -> {
            List<Map> artists = (List<Map>) channels.get(type);
            //保存主题模型
            List<Subject> subjects = new ArrayList<>();
            artists.forEach(map -> {
                Subject subject = new Subject();
                subject.setId(map.get("id").toString());
                subject.setCover(map.get("cover").toString());
                subject.setName(map.get("name").toString());
                subject.setDescription(map.get("intro").toString());
                subject.setSubjectType(SubjectUtil.TYPE_MHZ);
                //从艺术家出发
                if (type.equals("artist")) {
                    subject.setSubjectSubType(SubjectUtil.TYPE_SUB_ARTIST);
                    subject.setMaster(map.get("artist_id").toString());
                    List<Map> relatedArtists = (List<Map>) map.get("related_artists");
                    //保存相关歌手id
                    //List<String> singerIds = new ArrayList<>();
                    //保存歌手模型
                    List<Singer> singers = new ArrayList<>();
                    relatedArtists.forEach(artist -> {
                        //singerIds.add(artist.get("id").toString());
                        Singer singer = new Singer();
                        singer.setId(artist.get("id").toString());
                        singer.setAvator(artist.get("cover").toString());
                        singer.setGmtCreated(LocalDateTime.now());
                        singer.setGmtModified(LocalDateTime.now());
                        singer.setName(artist.get("name").toString());
                        //如果不存在次歌手则保存
                        if (singerService.get(singer.getId()) == null) {
                            singerService.addSinger(singer);
                        }
                    });
                } else if (type.equals("scenario")) {
                    subject.setSubjectSubType(SubjectUtil.TYPE_SUB_MOOD);
                } else if (type.equals("language")) {
                    subject.setSubjectSubType(SubjectUtil.TYPE_SUB_AGE);
                } else if (type.equals("genre")) {
                    subject.setSubjectSubType(SubjectUtil.TYPE_SUB_STYLE);
                }
                subject.setPublishedDate(LocalDateTime.now());
                subject.setGmtCreated(LocalDateTime.now());
                subject.setGmtCreated(LocalDateTime.now());
                //如果不存在就保存
                if (subjectService.get(subject.getId()) == null) {
                    subjectService.addSubject(subject);
                }
                getSubjectSongData(subject.getId());
            });
        });
    }

    /**
     * 爬取主题歌曲并保存
     */
    //@Test
    public void getSubjectSongData(String subjectId) {
        String subjectSongData = httpUtil.getContent("https://fm.douban.com/j/v2/playlist?channel=" + subjectId + "&kbps=128&client=s%3Amainsite%7Cy%3A3.0&app_name=radio_website&version=100&type=n", header);
        Map songData = JSON.parseObject(subjectSongData, Map.class);
        List<Map> songList = (List<Map>) songData.get("song");
        songList.forEach(item -> {
            Song song = new Song();
            song.setName(item.get("title").toString());
            song.setId(item.get("sid").toString());
            song.setCover(item.get("picture").toString());
            song.setUrl(item.get("url").toString());
            song.setGmtCreated(LocalDateTime.now());
            song.setGmtModified(LocalDateTime.now());
            List<Map> singers = (List<Map>) item.get("singers");
            List<String> singerIds = new ArrayList<>();
            singers.forEach(singer -> {
                singerIds.add(singer.get("id").toString());
                Singer singer1 = new Singer();
                singer1.setId(singer.get("id").toString());
                singer1.setName(singer.get("name").toString());
                if (singerService.get(singer1.getId())==null){
                    singerService.addSinger(singer1);
                }
            });
            song.setSingerIds(singerIds);
            //如果不存在则保存
            if (songService.get(song.getId()) == null) {
                songService.add(song);
            }
        });
    }

    /**
     * 歌手歌曲爬取
     */
    public void getSongDataBySingers(Singer singer) {
        String returnData = httpUtil.getContent("https://fm.douban.com/j/v2/artist/" + singer.getId() + "/", header);
        Map songData = JSON.parseObject(returnData, Map.class);
        Map songList = (Map) songData.get("songlist");
        List<Map> songs = (List<Map>) songList.get("songs");
        songs.forEach(map -> {
            Song song = new Song();
            song.setName(map.get("title").toString());
            song.setCover(map.get("picture").toString());
            song.setId(map.get("sid").toString());
            song.setUrl(map.get("url").toString());
            //如果不存在则添加
            if (songService.get(song.getId()) == null) {
                songService.add(song);
            }
        });
        getRelatedSingers(songData,singer);
    }

    private void getRelatedSingers(Map sourceData,Singer Topsinger) {
        Map relatedChannel = (Map) sourceData.get("related_channel");
        List<Map> similarArtists = (List<Map>) relatedChannel.get("similar_artists");
        List<String> similarSingersId = new ArrayList<>();
        similarArtists.forEach(map -> {
            Singer singer = new Singer();
            singer.setName(map.get("name").toString());
            singer.setId(map.get("id").toString());
            similarSingersId.add(singer.getId());
            singer.setAvator(map.get("avatar").toString());
            singer.setGmtCreated(LocalDateTime.now());
            singer.setGmtModified(LocalDateTime.now());
            if (singerService.get(singer.getId()) == null) {
                singerService.addSinger(singer);
            }
        });
        if (Topsinger.getSimilarSingerIds()==null){
            Topsinger.setSimilarSingerIds(similarSingersId);
            singerService.modify(Topsinger);
        }
    }
    public void getCollection(){
        String returnData = httpUtil.getContent("https://douban.fm/j/v2/songlist/explore?type=hot&genre=0&limit=20&sample_cnt=5",header);
        System.out.println(returnData);
        List collection = JSON.parseObject(returnData,List.class);
       // System.out.println(collection==null);
        collection.forEach(item->{
            Map map = (Map)item;
            Subject subject = new Subject();
            subject.setCover(map.get("cover").toString());
            DateTimeFormatter df= DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            subject.setGmtCreated(LocalDateTime.parse(map.get("created_time").toString(),df));
            subject.setGmtModified(LocalDateTime.now());
            subject.setId(map.get("id").toString());
            System.out.println(subject.getId());
            subject.setSubjectType(SubjectUtil.TYPE_COLLECTION);
            subject.setDescription(map.get("intro").toString());
            subject.setName(map.get("title").toString());
            System.out.println(subject.getName());
            Map creator = (Map)map.get("creator");
            subject.setMaster(creator.get("id").toString());
            //如果歌单不存在则保存
            if (subjectService.get(subject.getId())==null){
                subjectService.addSubject(subject);
            }
            //歌单作者（用歌手模型保存）
            Singer singer = new Singer();
            singer.setId(creator.get("id").toString());
            singer.setName(creator.get("name").toString());
            singer.setAvator(creator.get("url").toString());
            //如果作者不存在则保存
            if (singerService.get(singer.getId())==null){
                singerService.addSinger(singer);
            }
        });

    }
}
