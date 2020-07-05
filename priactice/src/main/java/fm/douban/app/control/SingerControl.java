package fm.douban.app.control;

import fm.douban.model.Singer;
import fm.douban.service.SingerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @ClassName SingerControl
 * @Author 刘正星
 * @Date 2020/6/21 20:51
 **/
@Controller
public class SingerControl {
    @Autowired
    private SingerService singerService;
    @GetMapping("/user-guide")
    public String myMhz(Model model){
        model.addAttribute("singers" ,randomSingers());
        return "userguide";
    }
    @GetMapping("/singer/random")
    @ResponseBody
    public List<Singer> randomSingers(){
        List<Singer> singerList = singerService.getAll();
        List<Singer> randomSingerList = new ArrayList<>();
        for (int i = 0;i <= 9;i++){
            Random random = new Random();
            int num = random.nextInt(100);
            randomSingerList.add(singerList.get(num));
        }
        return randomSingerList;
    }
}
