package travel.service;

import travel.base.service.BaseService;
import travel.dto.ShopDto;
import travel.entity.ShopEntity;

import java.util.List;

/**
 * <p>
 * 购物车 服务类
 * </p>
 *
 * @author wang
 * @since 2023-03-28
 */
public interface ShopService extends BaseService<ShopEntity> {
    /**
     * 查询详情
     *
     * @param id 数据id
     */
    ShopEntity queryInfo(Long id);

    /**
     * 新增
     *
     * @param shopDto 数据
     */
    ShopEntity insert(ShopDto shopDto);

    /**
     * 修改
     *
     * @param shopDto 数据
     */
    ShopEntity update(ShopDto shopDto);

    /**
     * 删除
     *
     * @param idList 数据id
     */
    void deleteBatch(List<Long> idList);
}
