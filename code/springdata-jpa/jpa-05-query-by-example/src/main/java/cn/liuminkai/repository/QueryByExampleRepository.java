package cn.liuminkai.repository;

import cn.liuminkai.pojo.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QueryByExampleRepository extends JpaRepository<Customer, Long> {

}
