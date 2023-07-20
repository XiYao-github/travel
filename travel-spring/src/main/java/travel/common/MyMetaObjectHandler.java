package travel.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {
    /**
     * 插入时填充
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("插入：填充公共字段[insert]..." + metaObject.toString());

        LocalDateTime nowDateTime = LocalDateTime.now();
        this.strictInsertFill(metaObject, "createdBy", Long.class, 1L);
        this.strictInsertFill(metaObject, "createdTime", LocalDateTime.class, nowDateTime);
        this.strictInsertFill(metaObject, "updatedBy", Long.class, 1L);
        this.strictInsertFill(metaObject, "updatedTime", LocalDateTime.class, nowDateTime);
    }

    /**
     * 更新时填充
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("更新：填充公共字段[update]..." + metaObject.toString());

        LocalDateTime nowDateTime = LocalDateTime.now();
        this.strictUpdateFill(metaObject, "updatedBy", Long.class, 2L);
        this.strictUpdateFill(metaObject, "updatedTime", LocalDateTime.class, nowDateTime);
    }
}
