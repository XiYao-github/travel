package travel.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.IllegalSQLInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisPlusConfig {

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        // 创建MybatisPlusInterceptor拦截器对象
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        // 添加分页拦截器
        mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        // 添加乐观锁拦截器
        //mybatisPlusInterceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        // SQL性能规范,验证索引
        //mybatisPlusInterceptor.addInnerInterceptor(new IllegalSQLInnerInterceptor());
        // 防止全表更新与删除
        //mybatisPlusInterceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        // 返回拦截器对象
        return mybatisPlusInterceptor;
    }
}
