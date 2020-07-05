package fm.douban.app.control;

import fm.douban.model.MhzViewModel;
import fm.douban.model.Singer;
import fm.douban.model.Song;
import fm.douban.model.Subject;
import fm.douban.param.SongQueryParam;
import fm.douban.service.SingerService;
import fm.douban.service.SongService;
import fm.douban.service.SubjectService;
import fm.douban.util.SubjectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
public class MainControl {
    private static final Logger LOG = LoggerFactory.getLogger(MainControl.class);
    @Autowired
    SongService songService;
    @Autowired
    SingerService singerService;
    @Autowired
    SubjectService subjectService;
  //首页
    @GetMapping("/index")
    public String index(Model model) {
        //播放器controller
        SongQueryParam songQueryParam = new SongQueryParam();
        songQueryParam.setPageSize(1);
        songQueryParam.setPageNum(1);
        Page<Song> songs = songService.list(songQueryParam);
        songs.forEach(song -> {
           List<String> singerIds = song.getSingerIds();
          // singerIds.forEach(System.out::println);
           List<Singer> singers = new ArrayList<>();
           singerIds.forEach(s -> {
               if (singerService.get(s)==null){
                   return;
               }else {
                   singers.add(singerService.get(s));
               }
           });
            model.addAttribute("singers",singers);
        });
        model.addAttribute("song", songs);
       // System.out.println(songs.getContent().get(0).getName());
        //兆赫Controller
        List<Subject> artistdatas = new ArrayList<>();
        artistdatas = subjectService.getSubjects(SubjectUtil.TYPE_MHZ,SubjectUtil.TYPE_SUB_ARTIST);
        MhzViewModel mood = new MhzViewModel();
        mood.setTitle("心情 / 场景");
        mood.setSubjects(subjectService.getSubjects(SubjectUtil.TYPE_MHZ,SubjectUtil.TYPE_SUB_MOOD));
        MhzViewModel age = new MhzViewModel();
        age.setTitle("语言 / 年代");
        age.setSubjects(subjectService.getSubjects(SubjectUtil.TYPE_MHZ,SubjectUtil.TYPE_SUB_AGE));
        MhzViewModel style = new MhzViewModel();
        style.setTitle("风格 / 流派");
        style.setSubjects(subjectService.getSubjects(SubjectUtil.TYPE_MHZ,SubjectUtil.TYPE_SUB_STYLE));
        List<MhzViewModel> mhzViewModelList = new ArrayList<>();
        mhzViewModelList.add(mood);
        mhzViewModelList.add(age);
        mhzViewModelList.add(style);
        model.addAttribute("artistDatas",artistdatas);
        model.addAttribute("mhzViewModel",mhzViewModelList);

        return "index";
    }
    //搜索页
    @GetMapping("/search")
    public String search(Model model){
        return "search";
    }
    @CrossOrigin(origins = "*")
    @GetMapping("/searchContent")
    @ResponseBody
    public Map searchContent(@RequestParam("keyword")String keyword){
        SongQueryParam songQueryParam = new SongQueryParam();
        songQueryParam.setPageNum(1);
        songQueryParam.setPageSize(10);
        songQueryParam.setName(keyword);
        Map<String,Page<Song>> songMap = new HashMap<>();
       songMap.put("songs",songService.list(songQueryParam));
        return songMap;
    }
    //搜索结果
}
