package travel.dict.interceptor;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import travel.dict.annotation.DataDict;
import travel.base.utils.SpringContextUtils;
import travel.dict.service.DictService;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.sql.Statement;
import java.util.*;

@Intercepts({
        @Signature(
                type = ResultSetHandler.class,
                method = "handleResultSets",
                args = {Statement.class}
        )
})
public class DataDictInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        return setFieldValue(invocation.proceed());
    }

    private Object setFieldValue(Object result) {
        //集合处理
        if (result instanceof Collection) {
            List<Object> list = new ArrayList<>();
            ((Collection<?>) result).parallelStream().forEachOrdered(obj -> {
                if (obj instanceof String || obj instanceof Number) {
                    //集合内基本类型处理
                    list.add(obj);
                } else {
                    //集合内对象类型处理
                    list.add(setFieldValue(obj));
                }
            });
            return list;
        }
        //基本类型处理
        if (result instanceof String || result instanceof Number) {
            return result;
        }
        //对象类型处理
        //获取对象类型Class类型
        Class<?> sourceClass = result.getClass();
        MetaObject metaObject = SystemMetaObject.forObject(result);
        //反射获取字段
        Field[] fields = sourceClass.getDeclaredFields();
        Arrays.stream(fields).parallel()
                //过滤
                .filter(field -> field.getAnnotation(DataDict.class) != null)
                //遍历
                .forEachOrdered(field -> {
                    DataDict dataDict = field.getAnnotation(DataDict.class);
                    setMetaObject(dataDict, field, metaObject);
                });
        return result;
    }

    public void setMetaObject(DataDict dataDict, Field field, MetaObject metaObject) {
        Object value = metaObject.getValue(field.getName());
        if (ObjectUtils.isEmpty(value)) {
            return;
        }
        //根据code获取数据字典字段
        DictService dictService = SpringContextUtils.getBean(DictService.class);
        //Map<String, String> dictMap = dictService.lambdaQuery().eq(DictEntity::getCode, dataDict.code())
        //        .list().parallelStream().collect(Collectors.toMap(DictEntity::getValue, DictEntity::getLabel));
        Map<String, String> dictMap = dictService.queryMap(dataDict.code());
        if (value instanceof CharSequence || value instanceof Number) {
            //根据键找值，赋值给指定target字段
            metaObject.setValue(dataDict.target(), dictMap.getOrDefault(value.toString(), ""));
        } else if (value.getClass().isArray()) {
            int length = Array.getLength(value);
            Object[] targetArr = new Object[length];
            for (int i = 0; i < length; i++) {
                //根据键找值
                targetArr[i] = dictMap.getOrDefault(Array.get(value, i).toString(), "");
            }
            //赋值给指定target字段
            metaObject.setValue(dataDict.target(), targetArr);
        } else if (value instanceof Collection<?>) {
            Collection<String> targetColl = new ArrayList<>();
            //遍历
            ((Collection<?>) value).parallelStream().forEachOrdered(val -> {
                //根据键找值
                targetColl.add(dictMap.getOrDefault(val.toString(), ""));
            });
            //赋值给指定target字段
            metaObject.setValue(dataDict.target(), targetColl);
        }
    }
}
