package travel.mapper;

import org.apache.ibatis.annotations.Mapper;
import travel.base.mapper.BaseMapper;
import travel.entity.PackagesEntity;

/**
 * <p>
 * 套餐 Mapper 接口
 * </p>
 *
 * @author wang
 * @since 2023-04-17
 */
@Mapper
public interface PackagesMapper extends BaseMapper<PackagesEntity> {

}
