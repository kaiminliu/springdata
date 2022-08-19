package cn.liuminkai.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 <!--用于整合jpa  @EnableJpaRepositories -->
 <jpa:repositories base-package="cn.liuminkai.repository"
 transaction-manager-ref="transactionManager"
 entity-manager-factory-ref="entityManagerFactory"></jpa:repositories>
 */
@Configuration
@EnableJpaRepositories("cn.liuminkai.repository") // @EnableJpaRepositories 相当于 jpa:repositories，里面的属性都一致
/**
    <!--启动注解方式的声明式事务-->
    <tx:annotation-driven transaction-manager="transactionManager"></tx:annotation-driven>
 */
@EnableTransactionManagement // 启动注解方式的声明式事务
public class JpaConfig {

    /**
     <!--EntityManagerFactory-->
     <bean name="entityManagerFactory" class="org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean">
        <property name="jpaVendorAdapter">
            <!--Hibernate实现-->
            <bean class="org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter">
            <!--生成数据库表-->
                <property name="generateDdl" value="true"></property>
                <property name="showSql" value="true"></property>
            </bean>
        </property>
         <!--设置实体类的包-->
         <property name="packagesToScan" value="cn.liuminkai.pojo"></property>
         <property name="dataSource" ref="dataSource" ></property>
     </bean>
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        // EntityManagerFactory
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        // Hibernate实现
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        // 生成数据库表
        adapter.setGenerateDdl(true);
        // 控制台打印SQL
        adapter.setShowSql(true);
        factory.setJpaVendorAdapter(adapter);
        // 设置实体类的包
        factory.setPackagesToScan("cn.liuminkai.pojo");
        // 设置数据源
        factory.setDataSource(dataSource());
        return factory;
    }

    /**
     <!--数据源-->
     <bean class="com.alibaba.druid.pool.DruidDataSource" name="dataSource">
         <property name="username" value="root"/>
         <property name="password" value="123456"/>
         <property name="driverClassName" value="com.mysql.jdbc.Driver"/>
         <property name="url" value="jdbc:mysql://localhost:3306/springdata_jpa?characterEncoding=UTF-8"/>
     </bean>
     */
    @Bean
    public DataSource dataSource() {
        // 数据源
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/springdata_jpa?characterEncoding=UTF-8");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
        return dataSource;
    }

    /**
        <!--声明式事务-->
        <bean class="org.springframework.orm.jpa.JpaTransactionManager" name="transactionManager">
            <property name="entityManagerFactory" ref="entityManagerFactory"></property>
        </bean>
     */
    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

}