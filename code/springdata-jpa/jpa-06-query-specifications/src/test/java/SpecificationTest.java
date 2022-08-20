import cn.liuminkai.config.JpaConfig;
import cn.liuminkai.pojo.Customer;
import cn.liuminkai.repository.SpecificationRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ContextConfiguration(classes = JpaConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class SpecificationTest {

    @Autowired
    private SpecificationRepository repository;

    // 1、无任何操作，默认findAll
    @Test
    public void findAll() {
        Specification<Customer> specification = new Specification<Customer>() {
            // root from Customer  , 获取列
            // CriteriaBuilder where 设置各种条件  (> < in ..)
            // query  组合（order by , where)
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return null;
            }
        };
        System.out.println(repository.findAll(specification));
    }

    // 2、使用 CriteriaBuilder root: custName.equals(x)
    @Test
    public void criteriaBuilderAndRootUseEquals() {

        System.out.println(repository.findAll((root, query, criteriaBuilder) -> {
            Path<Object> custName = root.get("custName");
            Predicate lisi = criteriaBuilder.equal(custName, "lisi");
            return lisi;
        }));
    }

    // 3、使用  CriteriaBuilder root，多条件 custName.equals(x)、custId > xx、custAddress in (xxx, xxxx)
    @Test
    public void UseEqualsAndGreaterThenAndIn() {
        repository.findAll((root, query, criteriaBuilder) -> {
            Path<Long> custId = root.get("custId");
            Path<String> custName = root.get("custName");
            Path<String> custAddress = root.get("custAddress");

            Predicate equal = criteriaBuilder.equal(custName, "Lisi");
            Predicate greaterThan = criteriaBuilder.greaterThan(custId, 21L);
            CriteriaBuilder.In<String> in = criteriaBuilder.in(custAddress).value("Beijing").value("Shanghai");

            Predicate p = criteriaBuilder.and(equal, greaterThan, in);
            return p;
        });
    }

    // 4. 使用  CriteriaBuilder root，多条件 custName.equals(x)、custId > xx、custAddress in (xxx, xxxx)
    @Test
    public void dynamicQueryTest() {

        // 需要查询的条件
        Customer params = new Customer();
        params.setCustName("Lisi");
        params.setCustAddress("Beijing,Shanghai");

        System.out.println(repository.findAll((root, query, criteriaBuilder) -> {
            Path<Long> custId = root.get("custId");
            Path<String> custName = root.get("custName");
            Path<String> custAddress = root.get("custAddress");

            if(params == null) {
                return null;
            }

            List<Predicate> predicateList = new ArrayList<>();

            // if custIdValue != null ==> custId > xx
            if(params.getCustId() != null && params.getCustId() > 0) {
                predicateList.add(criteriaBuilder.greaterThan(custId, params.getCustId()));
            }

            // if custNameValue != null and custNameValue != "" ==> custName.equals(x)
            if (StringUtils.hasText(params.getCustName())) {
                predicateList.add(criteriaBuilder.equal(custName, params.getCustName()));
            }

            List<String> addressList = Arrays.asList(params.getCustAddress().split(","));

            // if custAddress is not empty ==> custAddress in (xxx, xxxx)
            if (!CollectionUtils.isEmpty(addressList)) {
                CriteriaBuilder.In<String> in = criteriaBuilder.in(custAddress);
                for (String address : addressList) {
                    in.value(address);
                }
                predicateList.add(in);
            }

            return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
        }));
    }


    // 4. 使用  CriteriaBuilder root query，多条件+排序 custName.equals(x)、custId > xx、custAddress in (xxx, xxxx)
    @Test
    public void dynamicQueryUseOrderTest() {
        // 需要查询的条件
        Customer params = new Customer();
        params.setCustName("Lisi");
        params.setCustAddress("Beijing,Shanghai");

        System.out.println(repository.findAll((root, query, criteriaBuilder) -> {
            Path<Long> custId = root.get("custId");
            Path<String> custName = root.get("custName");
            Path<String> custAddress = root.get("custAddress");
            Path<Boolean> dataType = root.get("dataType");

            if(params == null) {
                return null;
            }

            List<Predicate> predicateList = new ArrayList<>();

            // if custIdValue != null ==> custId > xx
            if(params.getCustId() != null && params.getCustId() > 0) {
                predicateList.add(criteriaBuilder.greaterThan(custId, params.getCustId()));
            }

            // if custNameValue != null and custNameValue != "" ==> custName.equals(x)
            if (StringUtils.hasText(params.getCustName())) {
                predicateList.add(criteriaBuilder.equal(custName, params.getCustName()));
            }

            List<String> addressList = Arrays.asList(params.getCustAddress().split(","));

            // if custAddress is not empty ==> custAddress in (xxx, xxxx)
            if (!CollectionUtils.isEmpty(addressList)) {
                CriteriaBuilder.In<String> in = criteriaBuilder.in(custAddress);
                for (String address : addressList) {
                    in.value(address);
                }
                predicateList.add(in);
            }

            // 按dataType排序
            Order dataTypeAsc = criteriaBuilder.asc(dataType);
            return query.where(predicateList.toArray(new Predicate[predicateList.size()])).orderBy(dataTypeAsc).getRestriction();
        }));
    }
}
