package fm.douban.app.config;

import fm.douban.app.interceptor.UserInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ClassName AppConfigurer
 * @Author 刘正星
 * @Date 2020/7/8 9:45
 **/
@Configuration
public class AppConfigurer implements WebMvcConfigurer {
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserInterceptor()).addPathPatterns("/app/**")
                .excludePathPatterns("/app/authenticate")//登录操作不需要登录
                .excludePathPatterns("/app/login")//登录页面不需要登录
                .excludePathPatterns("/app/register")
                .excludePathPatterns("/app/sign")
                .excludePathPatterns("/css/**") // 静态资源为文件不需要登录
                .excludePathPatterns("/error");// 系统错误页面不需要登录
    }

}
