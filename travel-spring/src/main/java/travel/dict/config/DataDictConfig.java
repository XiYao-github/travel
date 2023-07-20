package travel.dict.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import travel.dict.interceptor.DataDictInterceptor;

@Configuration
public class DataDictConfig {

    @Bean
    public DataDictInterceptor dataDictInterceptor() {
        return new DataDictInterceptor();
    }
}
