package fm.douban.app.control;

import fm.douban.model.User;
import fm.douban.model.UserLoginInfo;
import fm.douban.param.UserQueryParam;
import fm.douban.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName UserControl
 * @Author 刘正星
 * @Date 2020/7/4 16:37
 **/
@Controller
@RequestMapping("/app")
public class UserControl {
    private static final Logger LOG = LoggerFactory.getLogger(UserControl.class);
    @Autowired
    private UserService userService;
    @PostConstruct
    public void init(){
        LOG.info("userControl 启动啦");
        LOG.info("userService 注入啦");
    }

    //注册页
    @GetMapping("/sign")
    public String signPage(Model model) {
        return "sign";
    }

    //提交注册
    @ResponseBody
    @PostMapping("/register")
    public Map registerAction(@RequestParam("name") String name, @RequestParam("password") String password, @RequestParam("mobile") String mobile, HttpServletRequest request, HttpServletResponse response) {
        Map returnData = new HashMap();

        // 判断登录名是否已存在
        User regiUser = getUserByLoginName(name);
        if (regiUser != null) {
            returnData.put("result", false);
            returnData.put("message", "login name already exists");
        }
        User user = new User();
        user.setLoginName(name);
        user.setMobile(mobile);
        user.setPassword(password);
        User newUser = userService.add(user);
        if (newUser != null && StringUtils.hasText(newUser.getId())) {
            returnData.put("result", true);
            returnData.put("message", "register success");
        } else {
            returnData.put("result", false);
            returnData.put("message", "register failed");
        }
        return returnData;

    }

    private User getUserByLoginName(String loginName) {
        User regedUser = null;
        UserQueryParam param = new UserQueryParam();
        param.setLoginName(loginName);
        Page<User> users = userService.list(param);

        // 如果登录名正确，只取第一个，要保证用户名不能重复
        if (users != null && users.getContent() != null && users.getContent().size() > 0) {
            regedUser = users.getContent().get(0);
        }

        return regedUser;
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        return "sign";
    }

    @ResponseBody
    @PostMapping("/authenticate")
    public Map login(@RequestParam("name") String name, @RequestParam("password") String password, HttpServletRequest request, HttpServletResponse response) {
        Map returnMap = new HashMap();
        User regisUser = null;
        regisUser = getUserByLoginName(name);
        if (regisUser == null){
            returnMap.put("result",false);
            returnMap.put("message","userName is not correct or don't have this user");
            return returnMap;
        }
        if (regisUser.getPassword().equals(password) ) {
            UserLoginInfo userLoginInfo = new UserLoginInfo();
            userLoginInfo.setUserId(regisUser.getId());
            userLoginInfo.setUserName(regisUser.getLoginName());
            // 取得 HttpSession 对象
            HttpSession session = request.getSession();
            // 写入登录信息
            session.setAttribute("userLoginInfo",userLoginInfo);
            returnMap.put("result", true);
            returnMap.put("message", "login success");
        }else{
            returnMap.put("result",false);
            returnMap.put("message","username or password not correct");
        }
        return returnMap;
    }

}
