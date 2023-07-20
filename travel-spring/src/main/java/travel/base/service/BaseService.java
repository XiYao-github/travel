package travel.base.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import travel.base.utils.ConvertUtils;
import travel.base.utils.PageData;

import java.io.Serializable;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public interface BaseService<T> extends IService<T> {

    QueryWrapper<T> queryWrapper(Map<String, Object> params);

    void handleList(List<T> list, Map<String, Object> params);


    default IPage<T> getPage(Map<String, Object> params) {
        // 分页参数
        long current = 1L;
        long size = 10L;
        if (!StrUtil.isBlankIfStr(params.get("current"))) {
            current = Long.parseLong(params.get("current").toString());
        }

        if (!StrUtil.isBlankIfStr(params.get("size"))) {
            size = Long.parseLong(params.get("size").toString());
        }
        // 分页对象
        Page<T> page = new Page<>(current, size);
        // 分页参数
        params.put("page", page);
        return page;
    }


    default <E> PageData<E> queryPage(Map<String, Object> params, Class<E> target) {
        return new PageData<>(this.queryPage(params), target);
    }

    default IPage<T> queryPage(Map<String, Object> params) {
        return this.queryPage(params, true);
    }

    default IPage<T> queryPage(Map<String, Object> params, Boolean isHandle) {
        IPage<T> page = this.page(getPage(params), this.queryWrapper(params));
        if (isHandle && !page.getRecords().isEmpty()) {
            this.handleList(page.getRecords(), params);
        }
        return page;
    }

    default List<T> queryList(Collection<? extends Serializable> idList) {
        List<T> list = this.listByIds(idList);
        if (!list.isEmpty()) {
            this.handleList(list, new HashMap<>());
        }
        return list;
    }

    default List<T> queryList(Map<String, Object> params, Boolean isHandle) {
        List<T> list = this.list(this.queryWrapper(params));
        if (isHandle && !list.isEmpty()) {
            this.handleList(list, params);
        }
        return list;
    }

    default <E> List<E> queryList(Map<String, Object> params, Boolean isHandle, Class<E> target) {
        List<T> list = this.list(this.queryWrapper(params));
        if (isHandle && !list.isEmpty()) {
            this.handleList(list, params);
        }
        return ConvertUtils.sourceToTarget(list, target);
    }

    default List<T> queryList(Wrapper<T> queryWrapper, Boolean isHandle) {
        List<T> list = this.list(queryWrapper);
        if (isHandle && !list.isEmpty()) {
            this.handleList(list, new HashMap<>());
        }
        return list;
    }
}
