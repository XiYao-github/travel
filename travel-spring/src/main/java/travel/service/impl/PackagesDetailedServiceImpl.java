package travel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import travel.base.service.impl.BaseServiceImpl;
import travel.entity.PackagesDetailedEntity;
import travel.mapper.PackagesDetailedMapper;
import travel.service.PackagesDetailedService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 套餐景点明细 服务实现类
 * </p>
 *
 * @author wang
 * @since 2023-03-28
 */
@Service
public class PackagesDetailedServiceImpl extends BaseServiceImpl<PackagesDetailedMapper, PackagesDetailedEntity> implements PackagesDetailedService {

    @Override
    public QueryWrapper<PackagesDetailedEntity> queryWrapper(Map<String, Object> params) {
        return null;
    }

    @Override
    public void handleList(List<PackagesDetailedEntity> list, Map<String, Object> params) {

    }

    @Override
    public List<Long> queryIdList(Long id) {
        return lambdaQuery().eq(PackagesDetailedEntity::getPackagesId, id).list()
                .stream().map(PackagesDetailedEntity::getScenicId).collect(Collectors.toList());
    }

    @Override
    public List<Long> queryIdList(List<Long> idList) {
        return lambdaQuery().in(PackagesDetailedEntity::getPackagesId, idList).list()
                .stream().map(PackagesDetailedEntity::getScenicId).collect(Collectors.toList());
    }

    @Override
    public void insert(Long packagesId, List<Long> ScenicIdList) {
        List<Long> idList = lambdaQuery().in(PackagesDetailedEntity::getPackagesId, packagesId)
                .list().stream().map(PackagesDetailedEntity::getId).collect(Collectors.toList());
        this.removeByIds(idList);
        List<PackagesDetailedEntity> list = new ArrayList<>();
        ScenicIdList.forEach(id -> {
            PackagesDetailedEntity entity = new PackagesDetailedEntity()
                    .setPackagesId(packagesId)
                    .setScenicId(id);
            list.add(entity);
        });
        this.saveBatch(list);
    }

}
