package travel.service;

import travel.base.service.BaseService;
import travel.dto.PackagesDto;
import travel.entity.PackagesEntity;

import java.util.List;

/**
 * <p>
 * 套餐 服务类
 * </p>
 *
 * @author wang
 * @since 2023-03-28
 */
public interface PackagesService extends BaseService<PackagesEntity> {
    /**
     * 查询详情
     *
     * @param id 数据id
     */
    PackagesEntity queryInfo(Long id);

    /**
     * 新增
     *
     * @param packagesDto 数据
     */
    PackagesEntity insert(PackagesDto packagesDto);

    /**
     * 修改
     *
     * @param packagesDto 数据
     */
    PackagesEntity update(PackagesDto packagesDto);

    /**
     * 删除
     *
     * @param idList 数据id
     */
    void deleteBatch(List<Long> idList);
}
