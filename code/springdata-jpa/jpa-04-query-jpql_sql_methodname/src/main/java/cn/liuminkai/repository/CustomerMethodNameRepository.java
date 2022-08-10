package cn.liuminkai.repository;

import cn.liuminkai.nameonly.CustomerNameOnly;
import cn.liuminkai.pojo.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerMethodNameRepository extends CrudRepository<Customer, Long> {

    List<Customer> findByCustAddressLike(String address);
    //List<CustomerNameOnly> findByCustAddressLike(String address);

    Customer findTopByCustAddress(String custAddress);

}
