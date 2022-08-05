import cn.liuminkai.pojo.Customer;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

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
        factory = Persistence.createEntityManagerFactory("hibernateJPA");
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
     *      有变动，就更新
     *      没变动，不更新
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

    @Test
    public void testSaveOrUpdate() {

        EntityManager em = factory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        // 开启事务
        transaction.begin();

        // 更新数据
        Customer customer = new Customer();
        customer.setCustId(1L);
        customer.setCustName("zhangsan");
        customer.setCustAddress("taibei");

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
        Customer customer = em.find(Customer.class, 1L);
        em.remove(customer);

        // 提交事务
        transaction.commit();
    }
}
