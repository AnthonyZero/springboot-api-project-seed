package com.anthonyzero.seed.datasources;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;


/**
 * 配置多数据源
 * @author pingjin create 2018年7月11日
 *
 */
@Configuration
@MapperScan("com.anthonyzero.seed.modules.*.mapper")
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
     * @throws Exception 
     */
    @Bean(name="sessionFactory")
    public SqlSessionFactoryBean sessionFactory(@Qualifier("firstDataSource") DataSource firstDataSource,
    		@Qualifier("secondDataSource") DataSource secondDataSource) throws Exception{
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(this.dataSource(firstDataSource, secondDataSource));
        sessionFactoryBean.setTypeAliasesPackage("com.anthonyzero.seed.modules.*.domain");
		
        sessionFactoryBean.setConfigLocation(new ClassPathResource("/mybatis.xml"));
        Resource[] mapperResources = new PathMatchingResourcePatternResolver()
        		.getResources("classpath:mapper/**/*.xml");
        sessionFactoryBean.setMapperLocations(mapperResources);   //Mapper.xml位置
        return sessionFactoryBean;
    }
}
