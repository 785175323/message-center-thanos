package com.kakuiwong.messagecenterthanos.util;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: gaoyang
 * @Description:
 */
public class CodeGenerator {

    public static void main(String[] args) {
        String url = "jdbc:MySql://127.0.0.1/test?characterEncoding=UTF-8&allowMultiQueries=true&useSSL=true";
        //项目实际路径
        String projectPath = "d:";
        //登陆账号
        String username = "root";
        //密码
        String password = "123456";
        //要生成的表
        String tables = "z_message_one";
        //生成代码的包名
        String parent = "com.kakuiwong.messagecenterthanos";
        //各模块包名
        String entity = "entity";
        String service = "service";
        String mapper = "mapper";
        String controller = "";
        //主键类型(下面是用户输入,还有自增啥的)
        IdType idType = IdType.INPUT;
        //逻辑删除字段
        String delete = "status";
        //乐观锁字段
        String version = "version";
        generator(url, username, password, projectPath, parent, entity, mapper, service, tables, idType, delete, version, controller);
    }

    public static void generator(String url, String username, String password
            , String projectPath, String parent, String entity, String mapper, String service
            , String tables, IdType idType, String delete, String version, String controller) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        // gc.setKotlin(true);
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor("gaoyang");
        gc.setOpen(false);
        gc.setDateType(DateType.TIME_PACK);
        gc.setIdType(idType);
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(url);
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername(username);
        dsc.setPassword(password);
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        //  pc.setModuleName("door-service");
        pc.setParent(parent);
        pc.setEntity(entity);
        pc.setServiceImpl(service + ".impl");
        pc.setMapper(mapper);
        pc.setService(service);
        pc.setController(controller);
        pc.setXml("");
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 如果模板引擎是 freemarker
        // String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
        String templatePath = "/templates/mapper.xml.vm";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        //自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/src/main/resources/mapper/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        /*
        cfg.setFileCreate(new IFileCreate() {
            @Override
            public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
                // 判断自定义文件夹是否需要创建
                checkDir("调用默认方法创建的目录");
                return false;
            }
        });
        */
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();
        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        // templateConfig.setEntity("templates/entity2.java");
        // templateConfig.setService();
        // templateConfig.setController();

      /*  templateConfig.setXml(null);
        templateConfig.setEntity("domain/%.java");
        templateConfig.setMapper("dao/%mapper.java");
        templateConfig.setService("service/%service.java");*/
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        //strategy.setSuperEntityClass("com.baomidou.ant.common.BaseEntity");
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        //strategy.setSuperControllerClass("com.baomidou.ant.common.BaseController");
        //设置表
        strategy.setInclude(tables);
        // strategy.setSuperEntityColumns("id");
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(pc.getModuleName() + "_");
        // strategy.setEntityTableFieldAnnotationEnable(true);
        strategy.setLogicDeleteFieldName(delete);
        if (StringUtils.isNotBlank(version)) {
            strategy.setVersionFieldName(version);
        }
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new VelocityTemplateEngine());
        mpg.execute();
    }
}