package travel.service;

import travel.base.service.BaseService;
import travel.entity.PackagesDetailedEntity;

import java.util.List;

/**
 * <p>
 * 套餐景点明细 服务类
 * </p>
 *
 * @author wang
 * @since 2023-03-28
 */
public interface PackagesDetailedService extends BaseService<PackagesDetailedEntity> {
    /**
     * 根据id查询
     *
     * @param id 数据id
     */
    List<Long> queryIdList(Long id);

    /**
     * 根据id集合查询
     *
     * @param idList 数据id
     */
    List<Long> queryIdList(List<Long> idList);

    /**
     * 新增
     *
     * @param packagesId    数据id
     * @param scenicIdList 数据id
     */
    void insert(Long packagesId, List<Long> scenicIdList);

}
