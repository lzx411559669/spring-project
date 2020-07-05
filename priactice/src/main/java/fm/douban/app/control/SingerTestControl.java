package fm.douban.app.control;

import fm.douban.model.Singer;
import fm.douban.service.SingerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/test/singer")
public class SingerTestControl {
    @Autowired
    private SingerService singerService;
    @RequestMapping("/add")
    public Singer testAddSinger(){
        Singer singer = new Singer();
        singer.setId("0");
        singer.setName("jack");
        return singerService.addSinger(singer);
    }
    @RequestMapping("/getAll")
    public List<Singer> testGetAll(){
       return singerService.getAll();
    }
    @RequestMapping("/getOne")
    public Singer testGetSinger(){
        return singerService.get("0");
    }
    @RequestMapping("/modify")
    public boolean testModifySinger(){
        Singer singer = new Singer();
        singer.setId("0");
        singer.setName("tony");
       return singerService.modify(singer);
    }
    @RequestMapping("/del")
    public boolean testDelSinger(){
        return singerService.delete("0");
    }
}
