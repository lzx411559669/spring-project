package fm.douban.app.control;

import fm.douban.model.Singer;
import fm.douban.model.Song;
import fm.douban.model.Subject;
import fm.douban.service.SingerService;
import fm.douban.service.SongService;
import fm.douban.service.SubjectService;
import fm.douban.util.SubjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName SubjectControl
 * @Author 刘正星
 * @Date 2020/6/23 19:05
 **/
@Controller
public class SubjectControl {
    @Autowired
    private SingerService singerService;
    @Autowired
    private SongService songService;
    @Autowired
    private SubjectService subjectService;
    //艺术家详情
    @GetMapping("/artist")
    public String mhzDetail(Model model, @RequestParam("subjectId") String subjectId) {
        //主题实例
        Subject subject = subjectService.get(subjectId);
        model.addAttribute("subject",subject);
        //歌曲实例
        List<Song> songs = new ArrayList<>();
        Singer singer = singerService.get(subject.getMaster());
        model.addAttribute("singer",singer);
        List<Song> allSongList = songService.getAll();
      /*  allSongList.forEach(song -> {
              if (song.getSingerIds()!=null){
                  song.getSingerIds().forEach(s -> {
                      if (s.equals(singer.getId())){
                          songs.add(song);
                      }
                  });

              }
        });*/
        songs.forEach(song -> {
            System.out.println(song.getName());
        });
        model.addAttribute("songs",songs);
        List<Singer> similarSingers = new ArrayList<>();
        singer.getSimilarSingerIds().forEach(s -> {
            similarSingers.add(singerService.get(s));
        });
        model.addAttribute("simSingers",similarSingers);

        return "mhzdetail";
    }
    //歌单列表
    @GetMapping("/collection")
    public String collection(Model model){
        List<Subject> collections = subjectService.getSubjects(SubjectUtil.TYPE_COLLECTION);
        List<Singer> creators = new ArrayList<>();
        collections.forEach(subject -> {
            Singer singer = singerService.get(subject.getMaster()) ;
            if (!creators.contains(singer)){
                creators.add(singer);
            }

        });
        model.addAttribute("collections",collections);
        model.addAttribute("collectionsCreator",creators);
        collections.forEach(subject -> {
            System.out.println(subject.getName());
        });
        creators.forEach(singer -> {
            System.out.println(singer.getName());
        });

        return "collection";
    }

    //歌单详情
    @GetMapping("/collectiondetail")
    public String collectionDetail(Model model,@RequestParam(name = "subjectId")String subjectId){
         Subject subject = null;
         subject = subjectService.get(subjectId);
         Singer singer = null;
         singer = singerService.get(subject.getMaster());
         List<Song> songs = null;
         songs = songService.getAll();
         Subject otherSubject = null;
         otherSubject = subjectService.get(subjectId);
         model.addAttribute("subject",subject);
         model.addAttribute("singer",singer);
         model.addAttribute("songs",songs);
         model.addAttribute("otherSubject",otherSubject);
        return "collectiondetail";
    }


}

