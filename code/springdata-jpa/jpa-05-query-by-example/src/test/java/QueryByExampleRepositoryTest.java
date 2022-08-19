import cn.liuminkai.config.JpaConfig;
import cn.liuminkai.pojo.Customer;
import cn.liuminkai.repository.QueryByExampleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.Optional;

@ContextConfiguration(classes = JpaConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class QueryByExampleRepositoryTest {

    @Autowired
    private QueryByExampleRepository repository;

    /**
        动态查询：对应参数有值才会参与 sql 的 and 拼接
        根据 custId、custName，custAddress 作为条件动态查询
     */
    @Test
    public void dynamicQueryTest() {
        // 制造 查询参数（可以注释其中的set语句，达到动态条件的效果）
        Customer customer = new Customer();
        //customer.setCustId(2L);
        //customer.setCustName("Lisi");
        //customer.setCustAddress("Beijing");
        // 支持的类型测试
        //
        // customer.setDataType(new Date(2022 - 1900, 8 - 1, 18)); // Date支持
        // customer.setDataType(true); // Boolean 支持，对应bit类型

        // 动态查询
        Example<Customer> example = Example.of(customer);
        System.out.println(repository.findAll(example));
    }

    /**
     *  为了便于上面 Example支持数据类型的测试 的测试，这里保存一条新数据
     */
    @Test
    public void saveDate() {
        Customer customer = new Customer();
        customer.setCustName("Lisi");
        customer.setCustAddress("Beijing");
        //customer.setDataType(new Date(2022 - 1900, 8 - 1, 18));
        //customer.setDataType(true);
        repository.save(customer);
    }



    /**
         动态查询：对应参数有值才会参与 sql 拼接
         根据 custId、custName，custAddress 作为条件动态查询
     ExampleMatcher
     */
    @Test
    public void dynamicQueryUseMatcherTest() {
        // 制造 查询参数（可以注释其中的set语句，达到动态条件的效果）
        Customer customer = new Customer();
        customer.setCustName("Lisi");
        customer.setCustAddress("JING");

        /*
            匹配器的使用
         */
        ExampleMatcher matcher = ExampleMatcher.matching();
        // 1. withIgnorePaths 忽略某些property参与匹配
        //matcher =
                ExampleMatcher.matching()
                .withIgnorePaths("custName");

        // 2. withStringMatcher 对所有字符串property进行匹配
        //matcher =
                ExampleMatcher.matching()
                // DEFAULT, EXACT, STARTING, ENDING, CONTAINING, REGEX;
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.ENDING
                );

        // 3. withIgnoreCase 忽略某些property大小写匹配
        // （默认是不忽略大小写，但是我的MySQL对值不区分大小写，就好像JPA忽略大小写一样）
        // 原因是 数据库的排序规则使用的是 xxx_ci ，是不区分大小写的；应该使用 xxx_bin ，他区分大小写
        //matcher =
                ExampleMatcher.matching()
                .withIgnoreCase("custName");
                // .withIgnoreCase(); // 忽略大小写
                // .withIgnoreCase(false); // 不忽略大小写

        // 4. withIgnoreNullValues 忽略null值匹配（默认）
        //matcher =
                ExampleMatcher.matching()
                .withIgnoreNullValues();

        // 6. withIncludeNullValues null值参与匹配，表现：property is null
        //matcher =
                ExampleMatcher.matching()
                .withIncludeNullValues();

        // 7. withNullHandler
        //matcher =
                ExampleMatcher.matching()
                // INCLUDE, IGNORE
                .withNullHandler(ExampleMatcher.NullHandler.IGNORE);

        // 8. withTransformer 属性值转换规则（允许查询前，更换属性值）
        //matcher =
                ExampleMatcher.matching()
                .withTransformer("custName", o -> Optional.of("lisi"));

        // 9. withMatcher 属性匹配规则（withMatcher 设置后，前面设置的withIgnoreCase就失效了，要想大小写不敏感，必须调用withMatcher的ignoreCase()）
        matcher =
                ExampleMatcher.matching()
                .withIgnoreCase("custAddress")
                // 参数2：GenericPropertyMatcher
                //.withMatcher("custName", ExampleMatcher.GenericPropertyMatchers)
                // 参数2：MatcherConfigurer<GenericPropertyMatcher>
                .withMatcher("custAddress", g -> g.endsWith().ignoreCase())
                    ;

        // 动态查询
        Example<Customer> example = Example.of(customer, matcher);
        System.out.println(repository.findAll(example));
    }
}
