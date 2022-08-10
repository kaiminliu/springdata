package cn.liuminkai.repository;

import cn.liuminkai.nameonly.CustomerNameOnly;
import cn.liuminkai.pojo.Customer;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CustomerSqlRepository extends CrudRepository<Customer, Long> {

    @Query(value = "select * from tb_customer where cust_address = :address", nativeQuery = true)
    List<Customer> findByCustAddressLike(@Param("address") String address);
    //List<CustomerNameOnly> findByCustAddressLike(@Param("address") String address);

    @Transactional
    @Modifying
    @Query(value = "insert into tb_customer values(:#{#cu.custId}, :#{#cu.custName}, :#{#cu.custAddress})", nativeQuery = true)
    int insertCustomer(@Param("cu") Customer cu);

    @Transactional
    @Modifying
    @Query(value = "update tb_customer set cust_address = :#{#cu.custAddress} where id = :#{#cu.custId}", nativeQuery = true)
    int updateCustomer(@Param("cu") Customer cu);


    @Transactional
    @Modifying
    @Query(value = "delete from tb_customer where id = ?1", nativeQuery = true)
    int deleteCustomer(Long id);
}
