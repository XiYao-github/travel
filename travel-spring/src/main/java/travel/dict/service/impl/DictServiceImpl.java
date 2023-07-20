package travel.dict.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import travel.base.service.impl.BaseServiceImpl;
import travel.dict.entity.DictEntity;
import travel.dict.mapper.DictMapper;
import travel.dict.service.DictService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 数据字典 服务实现类
 * </p>
 *
 * @author wang
 * @since 2023-03-28
 */
@Service
public class DictServiceImpl extends BaseServiceImpl<DictMapper, DictEntity> implements DictService {

    @Override
    public QueryWrapper<DictEntity> queryWrapper(Map<String, Object> params) {
        return null;
    }

    @Override
    public void handleList(List<DictEntity> list, Map<String, Object> params) {

    }

    @Override
    public List<DictEntity> queryList(String code) {
        return this.lambdaQuery().eq(DictEntity::getCode, code).list();
    }

    @Override
    public Map<String, String> queryMap(String code) {
        List<DictEntity> dictList = this.queryList(code);
        return dictList.parallelStream().collect(Collectors.toMap(DictEntity::getValue, DictEntity::getLabel));
    }
}
