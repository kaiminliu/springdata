import cn.liuminkai.pojo.Customer;
import cn.liuminkai.repository.CustomerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;



@ContextConfiguration("/spring.xml") // 指明spring上下文配置文件
@RunWith(SpringJUnit4ClassRunner.class)
public class JpaXmlTest {

    @Autowired
    private CustomerRepository customerRepository;

    /**
     * 保存
     * custId 是无效的
     */
    @Test
    public void testSave() {
        Customer customer = new Customer();
        customer.setCustId(3L);
        customer.setCustName("WangWu");
        customer.setCustAddress("Beijing");
        Customer c = customerRepository.save(customer);
        System.out.println(c);
    }
}
