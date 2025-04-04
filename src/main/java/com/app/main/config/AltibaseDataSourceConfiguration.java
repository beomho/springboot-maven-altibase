package com.app.main.config; 

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@MapperScan(value="com.app.main.altibase.mapper")
public class AltibaseDataSourceConfiguration {

    @Bean
    @Primary
    public HikariDataSource dataSource() {
    	
    	HikariConfig hikariConfig = new HikariConfig();    
    	hikariConfig.setDriverClassName("Altibase.jdbc.driver.AltibaseDriver");    
//    	hikariConfig.setJdbcUrl("jdbc:Altibase://175.114.170.27:22007/SSCSGDEV");
    	hikariConfig.setJdbcUrl("jdbc:Altibase://172.16.4.107:22007/SSCSG");
    	hikariConfig.setUsername("safety");
    	hikariConfig.setPassword("safetykeeper");
    	hikariConfig.setMaximumPoolSize(5);
    	hikariConfig.setConnectionTestQuery("SELECT 1");
    	hikariConfig.setPoolName("springHikariCP");
    	HikariDataSource dataSource = new HikariDataSource(hikariConfig);

        return dataSource;
    }

}