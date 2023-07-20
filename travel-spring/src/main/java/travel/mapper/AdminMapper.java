package travel.mapper;

import org.apache.ibatis.annotations.Mapper;
import travel.base.mapper.BaseMapper;
import travel.entity.AdminEntity;

/**
 * <p>
 * 管理员 Mapper 接口
 * </p>
 *
 * @author wang
 * @since 2023-04-17
 */
@Mapper
public interface AdminMapper extends BaseMapper<AdminEntity> {

}
