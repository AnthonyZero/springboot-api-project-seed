package com.anthonyzero.seed.datasources;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.extension.parsers.BlockAttackSqlParser;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 配置多数据源
 * @author pingjin create 2018年7月11日
 *
 */
@Configuration
public class DynamicDataSourceConfig {

    @Bean
    @ConfigurationProperties("spring.datasource.druid.first")
    public DataSource firstDataSource(){
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.druid.second")
    public DataSource secondDataSource(){
        return DruidDataSourceBuilder.create().build();
    }

    /**
     *  @Qualifier 根据名称进行注入，通常是在具有相同的多个类型的实例的一个注入（例如有多个DataSource类型的实例）
     * @param firstDataSource
     * @param secondDataSource
     * @return
     */
    @Bean(name="dynamicDataSource")
    @Primary
    public DynamicDataSource dataSource(@Qualifier("firstDataSource") DataSource firstDataSource, 
    		@Qualifier("secondDataSource") DataSource secondDataSource) {
        Map<String, DataSource> targetDataSources = new HashMap<>();
        targetDataSources.put(DataSourceNames.FIRST, firstDataSource);
        targetDataSources.put(DataSourceNames.SECOND, secondDataSource);
        return new DynamicDataSource(firstDataSource, targetDataSources);
    }


    @Bean(name = "transactionManager")
    public DataSourceTransactionManager dataSourceTransactionManager(DynamicDataSource dataSource) {
        DataSourceTransactionManager txManager = new DataSourceTransactionManager();
        txManager.setDataSource(dataSource);
        return txManager;
    }



    /**
     * 根据数据源创建SqlSessionFactory
     * 使用MybatisPlus的时候 需要将mybatis的sqlSessionFactory替换成mybatis-plus的MybatisSqlSessionFactoryBean
     * @throws Exception 
     */
    @Bean(name="sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("firstDataSource") DataSource firstDataSource,
                                               @Qualifier("secondDataSource") DataSource secondDataSource) throws Exception{
        MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(this.dataSource(firstDataSource, secondDataSource)); //数据源
        sqlSessionFactory.setTypeAliasesPackage("com.anthonyzero.seed.modules.*.entity"); //使用别名

        //sqlSessionFactory.setConfigLocation(new ClassPathResource("/mybatis.xml")); //MyBatis 配置文件位置
        Resource[] mapperResources = new PathMatchingResourcePatternResolver()
        		 .getResources("classpath:mapper/**/*.xml"); // Mapper xml 扫描路径
        sqlSessionFactory.setMapperLocations(mapperResources);

        //MybatisConfiguration 在这替换配置文件 configuration和configurationLocation只能存在其中一个
        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setJdbcTypeForNull(JdbcType.NULL);
        configuration.setMapUnderscoreToCamelCase(true); //是否开启自动驼峰命名规则
        sqlSessionFactory.setConfiguration(configuration);

        GlobalConfig globalConfig = new GlobalConfig(); //GlobalConfig
        globalConfig.setBanner(false); //关闭 mybatis-plus的 banner
        sqlSessionFactory.setGlobalConfig(globalConfig);

        return sqlSessionFactory.getObject();
    }


    /**
     * 攻击 SQL 阻断解析器
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        List<ISqlParser> sqlParserList = new ArrayList<>();
        // 攻击 SQL 阻断解析器、加入解析链
        sqlParserList.add(new BlockAttackSqlParser());
        paginationInterceptor.setSqlParserList(sqlParserList);
        return paginationInterceptor;
    }
}
