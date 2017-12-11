package ph.doctorplus.authserver.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class MVCConfig extends WebMvcConfigurerAdapter {

//    @Override
//    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
//        configurer.enable();
//    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

        registry.addViewController("/").setViewName("forward:/index");
        registry.addViewController("/index").setViewName("index.html");
        registry.addViewController("/login").setViewName("login.html");
//        registry.addViewController("/signup").setViewName("signup.html");

    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost", "http://127.0.0.1")
                .allowedMethods("GET", "PUT", "POST", "DELETE", "OPTIONS");
    }
}
