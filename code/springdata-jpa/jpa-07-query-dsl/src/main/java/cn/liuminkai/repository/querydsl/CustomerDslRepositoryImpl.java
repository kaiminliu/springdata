package cn.liuminkai.repository.querydsl;

import cn.liuminkai.pojo.Customer;
import cn.liuminkai.pojo.querydsl.QCustomer;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

public class CustomerDslRepositoryImpl extends QuerydslPageSortSupport implements CustomerDslRepository {

    public CustomerDslRepositoryImpl(Class<Customer> customerClass) {
        super(customerClass);
    }

    public CustomerDslRepositoryImpl() {
        this(Customer.class);
    }

    @Override
    public List<Customer> selectAll(Customer params) {

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

        // 部分字段
        List<Customer> customerList = from(customer)
                .where(condition)
                .orderBy(customer.dataType.asc())
                .select(customer.custName, customer.dataType)
                .fetch()
                .stream().map(tuple -> {
                    Customer c = new Customer();
                    c.setCustName(tuple.get(customer.custName));
                    c.setDataType(tuple.get(customer.dataType));
                    return c;
                }).collect(Collectors.toList());
        return customerList;
    }
}
