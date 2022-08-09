import cn.liuminkai.config.JpaConfig;
import cn.liuminkai.pojo.Customer;
import cn.liuminkai.repository.CustomerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(classes = JpaConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class CrudTest {

    @Autowired
    private CustomerRepository customerRepository;

    /**
        1. 新增或更新
            无id，新增
            有id，先查
                没记录，新增
                有记录，有改动时才更新，没改动就不更新
     */
    @Test
    public void testInsertOrUpdate() {
        Customer customer = new Customer();
        customer.setCustId(5L);
        customer.setCustName("lisi");
        customer.setCustAddress("Shenzhen");
        System.out.println("customerRepository.save(customer) = " + customerRepository.save(customer));
        // saveAll、saveAndFlush、saveAllAndFlush
    }

    /**
        2. 查询
     */
    @Test
    public void testSelect() {

        System.out.println("customerRepository.findAll() = " + customerRepository.findAll());
        System.out.println("customerRepository.count() = " + customerRepository.count());
        System.out.println("customerRepository.existsById(1L) = " + customerRepository.existsById(1L));
        // findBy、findById、findAllById、findOne、getById、getOne、exists
    }

    /**
        3. 删除
        repo.delete(customer); 底层会帮我们先查询一下（游离=>持久），再删除，查不到就不删除
     */
    @Test
    public void testDelete() {

        Customer customer = new Customer();
        //customer.setCustId(1L);
        customer.setCustAddress("ShenZhen");
        customerRepository.delete(customer);
        // deleteById、deleteAll、deleteAllById、deleteInBatch、deleteAllInBatch、deleteAllByIdInBatch
    }
}
