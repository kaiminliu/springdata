package cn.liuminkai.repository;

import cn.liuminkai.nameonly.CustomerNameOnly;
import cn.liuminkai.pojo.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerSqlRepository extends CrudRepository<Customer, Long> {

    @Query(value = "select * from tb_customer where cust_address = :address", nativeQuery = true)
    List<Customer> findByCustAddressLike(@Param("address") String address);
    //List<CustomerNameOnly> findByCustAddressLike(@Param("address") String address);
}
