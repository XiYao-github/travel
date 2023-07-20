package travel.base.generator;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.fill.Column;
import travel.base.entity.BaseEntity;
import travel.base.mapper.BaseMapper;
import travel.base.service.BaseService;
import travel.base.service.impl.BaseServiceImpl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CodeGenerator {

    private final static String PROJECT_PATH = System.getProperty("user.dir");

    public static void main(String[] args) {
        System.out.println(PROJECT_PATH);
        generator();
    }

    public static void generator() {
        //String url = "jdbc:mysql://localhost:3306/travel?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&useSSL=false";
        String url = "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&useSSL=false";
        String username = "root";
        String password = "root";
        FastAutoGenerator.create(url, username, password)
                //全局配置
                .globalConfig((scanner, builder) -> builder
                        //设置作者
                        //.author(scanner.apply("请输入作者名称？"))
                        .author("wang")
                        //开启 swagger 模式
                        //.enableSwagger()
                        //覆盖已生成文件 默认值:false
                        .fileOverride()
                        //禁止打开输出目录 默认值:true
                        .disableOpenDir()
                        //指定输出目录
                        //.outputDir(PROJECT_PATH + "/travel-Spring/src/main/java")
                        //.outputDir(PROJECT_PATH + "/src/main/java")
                        .outputDir(PROJECT_PATH + "/code")
                )
                //包配置
                .packageConfig((scanner, builder) -> builder
                        //设置父包名 默认值:com.baomidou
                        .parent(scanner.apply("请输入包名？"))
                        //.parent("travel")
                        //父包模块名 默认值:无
                        //.moduleName("sys")
                        //Controller 包名 默认值:controller
                        .controller("controller")
                        //Entity 包名 默认值:entity
                        .entity("entity")
                        //Mapper XML 包名 默认值:mapper,mapper.xml
                        .mapper("mapper").xml("mapper.xml")
                        //Service Impl 包名 默认值:service,service.impl
                        .service("service").serviceImpl("service.impl")
                )
                // 策略配置
                .strategyConfig((scanner, builder) -> builder
                                //设置需要生成的表名
                                .addInclude(getTables(scanner.apply("请输入表名，多个英文逗号分隔？所有输入 all")))
                                //.addInclude(getTables("user"))
                                //设置过滤表前缀
                                .addTablePrefix("travel_")

                                //控制器配置 -> 开启驼峰转连字符("/user-entity") -> 开启生成@RestController控制器
                                .controllerBuilder().enableHyphenStyle().enableRestStyle()

                                //实体类配置 -> 禁用生成 serialVersionUID -> 开启链式模型 -> 开启 lombok 模型 -> 开启生成实体时生成字段注解(数据库字段)
                                .entityBuilder().disableSerialVersionUID().enableChainModel().enableLombok().enableTableFieldAnnotation()
                                //设置父类(自定义)BaseEntity.class
                                //.superClass(BaseEntity.class)
                                //逻辑删除字段名(数据库) -> 逻辑删除属性名(实体) -> 全局主键类型 -> 全局主键类型
                                .logicDeleteColumnName("del_flag").logicDeletePropertyName("delFlag").idType(IdType.AUTO).formatFileName("%sEntity")
                                //添加表字段填充
                                //.addTableFills(new Column("created_time", FieldFill.INSERT), new Column("updated_time", FieldFill.INSERT_UPDATE))
                                //添加父类公共字段 -> 格式化文件名称
                                //.addSuperEntityColumns("id", "created_by", "created_time", "updated_by", "updated_time", "del_flag", "version")

                                //mapper配置 -> 开启 @Mapper 注解 -> 启用 BaseResultMap(通用查询映射结果) -> 启用 BaseColumnList(通用查询结果列)
                                .mapperBuilder().enableMapperAnnotation().enableBaseResultMap().enableBaseColumnList()
                                //格式化 mapper 文件名称 -> 格式化 xml 实现类文件名称
                                .formatMapperFileName("%sMapper").formatXmlFileName("%sMapper")
                                //设置父类(自定义) BaseMapper.class
                                //.superClass(BaseMapper.class)

                                //Service配置 -> 格式化Service文件名称
                                .serviceBuilder().formatServiceFileName("%sService").formatServiceImplFileName("%sServiceImpl")
                        //设置 service 接口父类(自定义) BaseService.class -> 设置 service 实现类父类(自定义) BaseServiceImpl.class
                        //.superServiceClass(BaseService.class).superServiceImplClass(BaseServiceImpl.class)
                )
                //模板引擎配置，默认 Velocity
                .execute();
    }

    // 处理 all 情况
    protected static List<String> getTables(String tables) {
        return "all".equals(tables) ? Collections.emptyList() : Arrays.asList(tables.split(","));
    }
}