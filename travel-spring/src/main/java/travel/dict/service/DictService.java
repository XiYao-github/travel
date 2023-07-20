package travel.dict.service;

import travel.dict.entity.DictEntity;
import travel.base.service.BaseService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 数据字典 服务类
 * </p>
 *
 * @author wang
 * @since 2023-03-28
 */
public interface DictService extends BaseService<DictEntity> {
    /**
     * 根据code获取
     *
     * @param code 编码
     */
    List<DictEntity> queryList(String code);

    /**
     * 根据code获取
     *
     * @param code 编码
     */
    Map<String, String> queryMap(String code);
}
