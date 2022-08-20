import cn.liuminkai.config.JpaConfig;
import cn.liuminkai.pojo.Customer;
import cn.liuminkai.pojo.querydsl.QCustomer;
import cn.liuminkai.repository.QueryDslRepository;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
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

        //List<Tuple> results = factory.select(customer.custName, customer.dataType)
        //        .from(customer)
        //        .where(condition)
        //        .orderBy(customer.dataType.asc())
        //        .fetch();

        JPAQuery<Tuple> results = factory.select(customer.custName, customer.dataType)
                .from(customer)
                .where(condition)
                .orderBy(customer.dataType.asc())
                .fetchAll();

        System.out.println(results);
    }

}
