package cn.liuminkai.repository.querydsl;

import cn.liuminkai.pojo.Customer;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

public interface CustomerDslRepository {

    List<Customer> selectAll(Customer customer);
}
