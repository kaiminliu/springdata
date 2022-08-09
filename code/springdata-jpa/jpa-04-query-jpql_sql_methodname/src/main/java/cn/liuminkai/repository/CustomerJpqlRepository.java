package cn.liuminkai.repository;

import cn.liuminkai.nameonly.CustomerNameOnly;
import cn.liuminkai.pojo.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerJpqlRepository extends CrudRepository<Customer, Long> {

    @Query(value = "from Customer")
    //List<Customer> findByCustAddressLike(String address);
    List<CustomerNameOnly> findByCustAddressLike(String address);
}
