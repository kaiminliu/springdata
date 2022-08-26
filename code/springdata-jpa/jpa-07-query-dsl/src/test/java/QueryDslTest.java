import cn.liuminkai.config.JpaConfig;
import cn.liuminkai.pojo.Customer;
import cn.liuminkai.pojo.querydsl.QCustomer;
import cn.liuminkai.repository.QueryDslRepository;
import cn.liuminkai.repository.querydsl.CustomerDslRepository;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

@ContextConfiguration(classes = JpaConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class QueryDslTest {

    @Autowired
    private QueryDslRepository repository;

    // custName.equals(x)、custId > xx、custAddress in (xxx, xxxx)
    @Test
    public void dynamicQueryTest() {
        // 需要查询的条件
        Customer params = new Customer();
        //params.setCustName("Lisi");
        //params.setCustAddress("Beijing,Shanghai");

        /*
            使用Q类进行动态查询
         */
        QCustomer customer = QCustomer.customer;
        // 初始查询条件 1=1
        BooleanExpression condition = customer.isNotNull().or(customer.isNull());
        // if custIdValue != null ==> custId > xx
        condition = params.getCustId() != null && params.getCustId() > 0
                ? condition.and(customer.custId.gt(params.getCustId())) : condition;
        // if custNameValue != null and custNameValue != "" ==> custName.equals(x)
        condition = StringUtils.hasText(params.getCustName())
                ? condition.and(customer.custName.eq(params.getCustName())) : condition;
        // if custAddress is not empty ==> custAddress in (xxx, xxxx)
        condition = StringUtils.hasText(params.getCustAddress())
                ? condition.and(customer.custAddress.in(params.getCustAddress().split(","))) : condition;
        System.out.println(repository.findAll(condition));
    }


    /*
        JPAQueryFactory使用
     */
    private JPAQueryFactory factory;

    @PersistenceContext
    private EntityManager entityManager;

    @Before
    public void init() {
        factory = new JPAQueryFactory(entityManager);
    }

    @Test
    public void dynamicQueryPlusTest() {
        // 需要查询的条件
        Customer params = new Customer();
        //params.setCustName("Lisi");
        //params.setCustAddress("Beijing,Shanghai");

        QCustomer customer = QCustomer.customer;
        // 初始查询条件 1=1
        BooleanExpression condition = customer.isNotNull().or(customer.isNull());
        // if custIdValue != null ==> custId > xx
        condition = params.getCustId() != null && params.getCustId() > 0
                ? condition.and(customer.custId.gt(params.getCustId())) : condition;
        // if custNameValue != null and custNameValue != "" ==> custName.equals(x)
        condition = StringUtils.hasText(params.getCustName())
                ? condition.and(customer.custName.eq(params.getCustName())) : condition;
        // if custAddress is not empty ==> custAddress in (xxx, xxxx)
        condition = StringUtils.hasText(params.getCustAddress())
                ? condition.and(customer.custAddress.in(params.getCustAddress().split(","))) : condition;

        //// 部分字段
        //List<Customer> customerList = factory.select(customer.custName, customer.dataType)
        //        .from(customer)
        //        .where(condition)
        //        .orderBy(customer.dataType.asc())
        //        .fetch()
        //        .stream().map(tuple -> {
        //            Customer c = new Customer();
        //            c.setCustName(tuple.get(customer.custName));
        //            c.setDataType(tuple.get(customer.dataType));
        //            return c;
        //        }).collect(Collectors.toList());

        //// 全部字段
        //List<Customer> customerList = factory.select(customer)
        //        .from(customer)
        //        .where(condition)
        //        .orderBy(customer.dataType.asc())
        //        .fetch();

        // 分组
        List<Tuple> customerList = factory.select(customer, customer.count())
                .from(customer)
                .where(condition)
                .groupBy(customer.custName)
                .fetch();


        System.out.println(customerList);
    }


    /*
        使用QuerydslPageSortSupport + 自定义Repository和RepositoryImpl
        1、编写接口CustomerDslRepository（接口内可以定义一些抽象方法，查询用的）
        2、实现接口CustomerDslRepository（继承QuerydslRepositorySupport），
        实现类的名字为CustomerDslRepositoryImpl，且与接口在同一目录下，在构造器中要添加实体类.class，不然运行报错
        3、实现类重写接口，在重写方法中做动态查询即可
        4、QueryDslRepository 去继承 CustomerDslRepository
        5、通过 QueryDslRepository 调用 被重写的查询方法
     */
    @Test
    public void useQuerydslPageSortSupport() {
        // 需要查询的条件
        Customer params = new Customer();
        params.setCustName("Lisi");
        params.setCustAddress("Beijing,Shanghai");
        System.out.println(repository.selectAll(params));
    }


    @Test
    public void test() {

    }
}
