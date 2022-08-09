import cn.liuminkai.config.JpaConfig;
import cn.liuminkai.repository.CustomerJpqlRepository;
import cn.liuminkai.repository.CustomerMethodNameRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(classes = JpaConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class MethodNameTest {

    @Autowired
    private CustomerMethodNameRepository repository;

    @Test
    public void find() {
        System.out.println(repository.findByCustAddressLike("Beijing"));
    }
}
