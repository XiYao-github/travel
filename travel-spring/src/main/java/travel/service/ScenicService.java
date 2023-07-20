package travel.service;

import travel.base.service.BaseService;
import travel.dto.ScenicDto;
import travel.entity.ScenicEntity;

import java.util.List;

/**
 * <p>
 * 景点 服务类
 * </p>
 *
 * @author wang
 * @since 2023-03-28
 */
public interface ScenicService extends BaseService<ScenicEntity> {
    /**
     * 查询详情
     *
     * @param id 数据id
     */
    ScenicEntity queryInfo(Long id);

    /**
     * 新增
     *
     * @param scenicDto 数据
     */
    ScenicEntity insert(ScenicDto scenicDto);

    /**
     * 修改
     *
     * @param scenicDto 数据
     */
    ScenicEntity update(ScenicDto scenicDto);

    /**
     * 删除
     *
     * @param idList 数据id
     */
    void deleteBatch(List<Long> idList);
}
