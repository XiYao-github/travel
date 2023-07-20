package travel.service;

import travel.base.service.BaseService;
import travel.dto.CommentDto;
import travel.entity.CommentEntity;

import java.util.List;

/**
 * <p>
 * 评价 服务类
 * </p>
 *
 * @author wang
 * @since 2023-03-28
 */
public interface CommentService extends BaseService<CommentEntity> {
    /**
     * 查询详情
     *
     * @param id 数据id
     */
    CommentEntity queryInfo(Long id);

    /**
     * 新增
     *
     * @param commentDto 数据
     */
    CommentEntity insert(CommentDto commentDto);

    /**
     * 修改
     *
     * @param commentDto 数据
     */
    CommentEntity update(CommentDto commentDto);

    /**
     * 删除
     *
     * @param idList 数据id
     */
    void deleteBatch(List<Long> idList);
}
