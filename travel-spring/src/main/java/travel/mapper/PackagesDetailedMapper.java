package travel.mapper;

import org.apache.ibatis.annotations.Mapper;
import travel.base.mapper.BaseMapper;
import travel.entity.PackagesDetailedEntity;

/**
 * <p>
 * 套餐景点明细 Mapper 接口
 * </p>
 *
 * @author wang
 * @since 2023-04-17
 */
@Mapper
public interface PackagesDetailedMapper extends BaseMapper<PackagesDetailedEntity> {

}
