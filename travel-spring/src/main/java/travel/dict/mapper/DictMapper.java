package travel.dict.mapper;

import org.apache.ibatis.annotations.Mapper;
import travel.base.mapper.BaseMapper;
import travel.dict.entity.DictEntity;

/**
 * <p>
 * 数据字典 Mapper 接口
 * </p>
 *
 * @author wang
 * @since 2023-04-17
 */
@Mapper
public interface DictMapper extends BaseMapper<DictEntity> {

}
