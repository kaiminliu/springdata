import cn.liuminkai.pojo.Customer;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.*;
import java.util.List;

/**
 * EntityManager 作用，与前面HibernateTest Session的联系
 * EntityTransaction 作用
 *
 */
public class JpaHibernateTest {

    EntityManagerFactory factory;

    @Before
    public void init() {
        // 1、创建 EntityManagerFactory
        //factory = Persistence.createEntityManagerFactory("hibernateJPA");
        factory = Persistence.createEntityManagerFactory("openJpa");
    }

    // 2、使用 jpa 完成增删改查
    /**
     * 2.1 新增
     */
    @Test
    public void testPersist() {

        EntityManager em = factory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        // 开启事务
        transaction.begin();

        // 新增数据
        Customer customer = new Customer();
        customer.setCustName("张三");
        customer.setCustAddress("Wuhan");
        em.persist(customer);

        // 提交事务
        transaction.commit();
    }

    /**
     * 2.2 查询
     */
    @Test
    public void testFind() {

        EntityManager em = factory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        // 开启事务
        transaction.begin();

        // 查询数据
        Customer customer = em.find(Customer.class, 1L);

        // 打印数据
        System.out.println("find result: customer = " + customer);

        // 提交事务
        transaction.commit();
    }

    /**
     * 2.3 更新
     * merge：
     *  1.没有id，会当新数据插入（请注释掉setCustId，再测试）
     *  2.有id，会先查（请解开注释setCustId，再测试）
     *      有变动，就更新（记录不存在，就插入新纪录，id自增）
     *      没变动，不更新
     *
     * openJPA 实现，没有id就是插入，有id时，必须保证记录是存在的，存在才能更新，否则抛出异常
     */
    @Test
    public void testMerge() {

        EntityManager em = factory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        // 开启事务
        transaction.begin();

        // 更新数据
        Customer customer = new Customer();
        customer.setCustId(1L);
        customer.setCustName("zhangsan");
        customer.setCustAddress("taibei");
        em.merge(customer);

        // 提交事务
        transaction.commit();
    }

    /**
     * 2.4 删除
     * remove：
     *  1. 不能直接remove，会抛出异常：IllegalArgumentException: Removing a detached instance
     *      不能移除游离的实例
     *  2. 先调用find方法查询数据映射到一个实例（处于持久态）上，再调用remove删除该实例
     */
    @Test
    public void testRemove() {

        EntityManager em = factory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        // 开启事务
        transaction.begin();

        // 删除数据（先查后删）
        Customer customer = em.find(Customer.class, 3L);
        em.remove(customer);

        // 提交事务
        transaction.commit();
    }

    // 3、使用 jpql
    @Test
    public void testUpdateJPQL() {
        EntityManager em = factory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        // 开启事务
        transaction.begin();

        // 使用 jpql
        Query query = em.createQuery("UPDATE Customer c set c.custName = :name, c.custAddress = :address where c.custId = :id");
        query.setParameter("id", 2L);
        query.setParameter("name", "Zhangsan");
        query.setParameter("address", "Taibei");
        query.executeUpdate();

        // 提交事务
        transaction.commit();
    }

    // 4、使用 sql
    @Test
    public void testSelectSQL() {
        EntityManager em = factory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        // 开启事务
        transaction.begin();

        // 使用 sql
        /*List list = em.createNativeQuery("select * from jpa_customer where id = :id", Customer.class)
                .setParameter("id", 2L)
                .getResultList();*/
        // openJPA 不支持 :，需要使用 ?
        List list = em.createNativeQuery("select * from jpa_customer where id = ?1", Customer.class)
                .setParameter(1, 2L)
                .getResultList();
        System.out.println("sql select: list = " + list);

        // 提交事务
        transaction.commit();
    }
}
