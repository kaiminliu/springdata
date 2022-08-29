import cn.liuminkai.config.JpaConfig;
import cn.liuminkai.pojo.Account;
import cn.liuminkai.pojo.Customer;
import cn.liuminkai.repository.AccountRepository;
import cn.liuminkai.repository.CustomerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * OneToOne单向测试，主键 维护在
 * 准备工作：
 *      删除数据表 tb_customer
 */
@ContextConfiguration(classes = JpaConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class OneWayTest {

    /**
     * 根据 CustomerRepository 去对 Account 进行操作，获取对应的测试结果
     */
    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void test() {
        Customer customer = Customer.builder()
                .custName("zhangsan001")
                .custAddress("beijing001")
                .account(
                        Account.builder()
                                .username("zhangsan001")
                                .password("123").build()
                ).build();
        System.out.println(customerRepository.save(customer));
    }

    @Test
    public void customerInsert() {

    }

    @Test
    public void customerSelect() {
        System.out.println(customerRepository.findAll());
    }

    @Test
    public void customerUpdate() {

    }

    @Test
    public void customerRemove() {

    }

    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void accountInsert() {

    }

    @Test
    public void accountSelect() {

    }

    @Test
    public void accountUpdate() {

    }

    @Test
    public void accountRemove() {

    }


}
