package travel.mapper;

import org.apache.ibatis.annotations.Mapper;
import travel.base.mapper.BaseMapper;
import travel.entity.NotesEntity;

/**
 * <p>
 * 游记 Mapper 接口
 * </p>
 *
 * @author wang
 * @since 2023-04-17
 */
@Mapper
public interface NotesMapper extends BaseMapper<NotesEntity> {

}
