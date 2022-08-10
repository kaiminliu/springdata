import cn.liuminkai.config.JpaConfig;
import cn.liuminkai.pojo.Customer;
import cn.liuminkai.repository.CustomerMethodNameRepository;
import cn.liuminkai.repository.CustomerSqlRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(classes = JpaConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class SqlTest {

    @Autowired
    private CustomerSqlRepository repository;

    @Test
    public void find() {
        System.out.println(repository.findByCustAddressLike("Beijing"));
    }

    @Test
    public void insert() {
        Customer customer = new Customer();
        customer.setCustName("李四");
        customer.setCustAddress("上海");
        repository.insertCustomer(customer);
    }

    @Test
    public void update() {
        Customer customer = new Customer();
        customer.setCustId(1L);
        customer.setCustAddress("Shanghai");
        repository.updateCustomer(customer);
    }

    @Test
    public void delete() {
        repository.deleteCustomer(1L);
    }
}