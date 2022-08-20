package cn.liuminkai.repository;

import cn.liuminkai.pojo.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.QuerydslJpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface QueryDslRepository extends JpaRepository<Customer, Long> , QuerydslPredicateExecutor<Customer> {

}
