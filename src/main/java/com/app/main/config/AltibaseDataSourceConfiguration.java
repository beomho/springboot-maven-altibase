package com.app.main.config; 

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@MapperScan(value="com.app.main.altibase.mapper",sqlSessionFactoryRef="altibaseSqlSessionFactory")
public class AltibaseDataSourceConfiguration {

 	// 알티베이스 연결을 위한 config
	 @Bean 
	 @Primary
	 @ConfigurationProperties(prefix="spring.altibase.datasource")
	 public DataSourceProperties altibaseDataSourceProp() {
		return new DataSourceProperties(); 
	 }
	
	 @Primary    
     @Bean(name = "altibaseDataSource")
     @ConfigurationProperties(prefix = "spring.altibase.datasource")
     public DataSource altibaseDataSource() {
         return altibaseDataSourceProp().initializeDataSourceBuilder().build();
     }

     @Primary
     @Bean(name = "altibaseSqlSessionFactory")
     public SqlSessionFactory altibaseSqlSessionFactory(@Qualifier("altibaseDataSource") DataSource altibaseDataSource,
                                 ApplicationContext applicationContext) throws Exception {
            SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
            sqlSessionFactoryBean.setDataSource(altibaseDataSource);
            sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:mybatis-mapper/altibase/*.xml"));
            return sqlSessionFactoryBean.getObject();
     }

     @Primary
     @Bean(name = "altibaseSqlSessionFactory")
     public SqlSessionTemplate altibaseSqlSessionFactory(@Qualifier("altibaseSqlSessionFactory") SqlSessionFactory altibaseSqlSessionFactory) {
         return new SqlSessionTemplate(altibaseSqlSessionFactory);
     }
}
