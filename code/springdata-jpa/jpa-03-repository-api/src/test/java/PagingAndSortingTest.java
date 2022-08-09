import cn.liuminkai.config.JpaConfig;
import cn.liuminkai.pojo.Customer;
import cn.liuminkai.repository.CustomerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;

@ContextConfiguration(classes = JpaConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class PagingAndSortingTest {

    @Autowired
    private CustomerRepository customerRepository;

    /**
        1.排序
     */
    @Test
    public void testSort() {
        Sort descByCustAddress = Sort.by(Sort.Order.desc("custAddress"));
        descByCustAddress = Sort.by("custAddress").descending();
        descByCustAddress = Sort.by(Sort.Direction.DESC, "custAddress");
        descByCustAddress = Sort.by(Arrays.asList(Sort.Order.desc("custAddress")));
        // TypeSort
        descByCustAddress = Sort.sort(Customer.class)
                .by(Customer::getCustAddress)
                .descending();
        System.out.println(customerRepository.findAll(descByCustAddress));
    }


    /**
        2.分页（可以使用排序）
        先limit，再count(id)
     */
    @Test
    public void testPage() {
        int index = 1;
        int size = 2;
        PageRequest pageRequest = PageRequest.of(index, size, Sort.Direction.DESC, "custAddress");
        Page<Customer> page = customerRepository.findAll(pageRequest);
        System.out.println("page.getTotalElements() = " + page.getTotalElements());
        System.out.println("page.getTotalPages() = " + page.getTotalPages());
        System.out.println("page.getContent() = " + page.getContent());
        System.out.println("page.isEmpty() = " + page.isEmpty());
    }

}
