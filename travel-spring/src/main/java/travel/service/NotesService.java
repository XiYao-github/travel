package travel.service;

import travel.base.service.BaseService;
import travel.dto.NotesDto;
import travel.entity.NotesEntity;

import java.util.List;

/**
 * <p>
 * 游记 服务类
 * </p>
 *
 * @author wang
 * @since 2023-03-29
 */
public interface NotesService extends BaseService<NotesEntity> {
    /**
     * 查询详情
     *
     * @param id 数据id
     */
    NotesEntity queryInfo(Long id);

    /**
     * 新增
     *
     * @param notesDto 数据
     */
    NotesEntity insert(NotesDto notesDto);

    /**
     * 修改
     *
     * @param notesDto 数据
     */
    NotesEntity update(NotesDto notesDto);

    /**
     * 删除
     *
     * @param idList 数据id
     */
    void deleteBatch(List<Long> idList);
}
