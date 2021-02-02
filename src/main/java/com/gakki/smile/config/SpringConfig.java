package com.gakki.smile.config;

import com.gakki.smile.core.annotation.EnableVertx;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@ComponentScan(basePackages = "com.gakki.smile")
@EnableVertx(basePackages = "com.gakki.smile")
@MapperScan("com.gakki.smile.mapper")
public class SpringConfig {

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {

        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();

        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setJdbcUrl("jdbc:postgresql://47.114.137.215:5432/pet");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres");

        factoryBean.setDataSource(dataSource);
        return factoryBean.getObject();
    }





}
