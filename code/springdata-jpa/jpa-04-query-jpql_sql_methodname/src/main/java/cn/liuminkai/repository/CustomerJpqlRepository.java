package cn.liuminkai.repository;

import cn.liuminkai.nameonly.CustomerNameOnly;
import cn.liuminkai.nameonly.ICustomerNameOnly;
import cn.liuminkai.pojo.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerJpqlRepository extends CrudRepository<Customer, Long> {

    @Query("from Customer")
    List<Customer> findByCustAddressLike(String address);

//    @Query(value = "select c.custName as custName from Customer c")
//    List<ICustomerNameOnly> findByCustAddressLike1(String address);
//
//    @Query(value = "select c.cust_name as custName from tb_customer c", nativeQuery = true)
//    List<ICustomerNameOnly> findByCustAddressLike2(String address);
//
//    @Query(value = "select c.custName as custName from Customer c")
//    List<CustomerNameOnly> findByCustAddressLike3(String address);
//
//    @Query(value = "select c.cust_name as custName from tb_customer c", nativeQuery = true)
//    List<CustomerNameOnly> findByCustAddressLike4(String address);
//
//    @Query(value = "select new cn.liuminkai.nameonly.CustomerNameOnly(c.custName) from Customer c")
//    List<CustomerNameOnly> findByCustAddressLike5(String address);
//
////    @Query(value = "select new cn.liuminkai.nameonly.CustomerNameOnly(c.cust_name) from tb_customer c", nativeQuery = true)
//    List<CustomerNameOnly> findByCustAddressLike6(String address);
//
//    // 单表 https://blog.csdn.net/qq_34626094/article/details/122236116
//    // https://blog.csdn.net/hotbeat/article/details/108432911
//    // 多表 https://blog.csdn.net/hurtseverywhere/article/details/112366574
//    // 多表，一对多等映射
//    // @Query 和 @OneToMany
}
