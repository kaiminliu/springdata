import cn.liuminkai.config.JpaConfig;
import cn.liuminkai.pojo.Customer;
import cn.liuminkai.repository.CustomerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;



@ContextConfiguration(classes = JpaConfig.class) // 指明spring上下文配置文件
@RunWith(SpringJUnit4ClassRunner.class)
public class JpaJavaConfigTest {

    @Autowired
    private CustomerRepository customerRepository;

    /**
     * 保存
     */
    @Test
    public void testSave() {
        Customer customer = new Customer();
        customer.setCustName("WangWu");
        customer.setCustAddress("Beijing");
        Customer c = customerRepository.save(customer);
        System.out.println(c);
    }
}
