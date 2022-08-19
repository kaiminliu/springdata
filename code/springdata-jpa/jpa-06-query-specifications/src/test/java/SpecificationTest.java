import cn.liuminkai.config.JpaConfig;
import cn.liuminkai.pojo.Customer;
import cn.liuminkai.repository.SpecificationRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.criteria.*;

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

    // fixme CriteriaBuilder root 使用: equals > in
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
}
