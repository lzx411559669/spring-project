package fm.douban.app.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @ClassName UserInterceptor
 * @Author 刘正星
 * @Date 2020/7/8 9:47
 **/

public class UserInterceptor implements HandlerInterceptor {
    private static final Logger LOG = LoggerFactory.getLogger(UserInterceptor.class);
    //controller方法执行之前
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        if (session.getAttribute("userLoginInfo")!=null){
            return true;
        }
        String url = "/app/sign";
        response.sendRedirect(url);
        return false;
    }
    //Controller方法执行之后

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }
    // 整个请求完成后（包括Thymeleaf渲染完毕）

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
