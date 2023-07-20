package travel.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.*;

import java.util.List;

/**
 * 跨域配置
 **/
@Configuration
public class MyWebMvcConfig implements WebMvcConfigurer {

    /**
     * 跨域
     *
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 设置允许跨域的路由
        registry.addMapping("/**")
                // 置允许跨域请求的域名
                .allowedOriginPatterns("*")
                //是否允许证书（cookies）
                .allowCredentials(true)
                //设置允许的方法
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                //.allowedMethods("*")
                //跨域允许时间
                .maxAge(3600);
    }
}
