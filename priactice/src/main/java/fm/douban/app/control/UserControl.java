package fm.douban.app.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @ClassName UserControl
 * @Author 刘正星
 * @Date 2020/7/4 16:37
 **/
@Controller
public class UserControl {
    //注册页
    @GetMapping("sign")
    public String signPage(Model model){
        return "sign";
    }
    //提交注册
    @PostMapping("/register")
    public Map registerAction(@RequestParam("name")String name, @RequestParam("password")String password, @RequestParam("mobile")String mobile, HttpServletRequest request, HttpServletResponse response){
    return null;

    }
}
