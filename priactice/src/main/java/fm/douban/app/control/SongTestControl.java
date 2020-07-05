package fm.douban.app.control;

import fm.douban.model.Song;
import fm.douban.param.SongQueryParam;
import fm.douban.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test/song")
public class SongTestControl {
    @Autowired
    private SongService songService;
    @RequestMapping("/add")
    public Song testAdd(){
        Song song = new Song();
        song.setId("0");
        song.setName("嘴甜情歌");
       return songService.add(song);
    }
    @RequestMapping("/get")
    public Song testGet(){
      return songService.get("0");
    }
    @RequestMapping("/list")
    public Page<Song> testList(){
        SongQueryParam songQueryParam = new SongQueryParam();
        songQueryParam.setPageNum(1);
        songQueryParam.setPageSize(5);
      return   songService.list(songQueryParam);
    }
    @RequestMapping("/modify")
    public boolean testModify(){
        Song song =new Song();
        song.setId("0");
        song.setName("ddd");
        return songService.modify(song);
    }
    @DeleteMapping("/del")
    public boolean testDelete(){
       return songService.delete("0");
    }

    @GetMapping("/pagesong")
    @ResponseBody
    public Page<Song> pageSong(@RequestParam("page")int page){
        SongQueryParam songQueryParam = new SongQueryParam();
        songQueryParam.setPageNum(page);
        songQueryParam.setPageSize(5);
        return songService.list(songQueryParam);
    }
}
