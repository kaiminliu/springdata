import cn.liuminkai.config.JpaConfig;
import cn.liuminkai.nameonly.CustomerNameOnly;
import cn.liuminkai.nameonly.ICustomerNameOnly;
import cn.liuminkai.pojo.Customer;
import cn.liuminkai.repository.CustomerJpqlRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.stream.Collectors;

@ContextConfiguration(classes = JpaConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class JpqlTest {

    @Autowired
    private CustomerJpqlRepository repository;

    @Test
    public void find() {
        System.out.println(repository.findByCustAddressLike(""));
    }

    // todo 讨论限制参数返回
    //@Test
    //public void find1() {
    //    List<Customer> list = repository.findByCustAddressLike1("").stream().map(e -> {
    //        Customer customer = new Customer();
    //        customer.setCustName(e.getCustName());
    //        return customer;
    //    }).collect(Collectors.toList());
    //    System.out.println(list);
    //}
    //
    //@Test
    //public void find2() {
    //    List<Customer> list = repository.findByCustAddressLike2("").stream().map(e -> {
    //        Customer customer = new Customer();
    //        customer.setCustName(e.getCustName());
    //        return customer;
    //    }).collect(Collectors.toList());
    //    System.out.println(list);
    //}
    //
    //@Test
    //public void find3() {
    //    List<Customer> list = repository.findByCustAddressLike3("").stream().map(e -> {
    //        Customer customer = new Customer();
    //        customer.setCustName(e.getCustName());
    //        return customer;
    //    }).collect(Collectors.toList());
    //    System.out.println(list);
    //}
    //
    //@Test
    //public void find4() {
    //    List<Customer> list = repository.findByCustAddressLike4("").stream().map(e -> {
    //        Customer customer = new Customer();
    //        customer.setCustName(e.getCustName());
    //        return customer;
    //    }).collect(Collectors.toList());
    //    System.out.println(list);
    //}
    //
    //@Test
    //public void find5() {
    //    List<CustomerNameOnly> list = repository.findByCustAddressLike5("");
    //    System.out.println(list);
    //}
    //
    //@Test
    //public void find6() {
    //    List<CustomerNameOnly> list = repository.findByCustAddressLike6("");
    //    System.out.println(list);
    //}
}
