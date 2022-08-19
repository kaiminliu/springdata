package cn.liuminkai.repository;

import cn.liuminkai.pojo.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SpecificationRepository extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {

}
