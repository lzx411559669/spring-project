package fm.douban.app.control;

import fm.douban.model.Singer;
import fm.douban.model.Song;
import fm.douban.model.Subject;
import fm.douban.service.SingerService;
import fm.douban.service.SongService;
import fm.douban.service.SubjectService;
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
}
