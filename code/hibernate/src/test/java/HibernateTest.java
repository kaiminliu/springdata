import cn.liuminkai.pojo.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * StandardServiceRegistry 作用
 * MetadataSources 作用
 * SessionFactory 作用
 * Session 作用
 */
public class HibernateTest {

    private SessionFactory sf;

    @Before
    public void init() {
        // 1、创建SessionFactory
        // 根据服务注册类创建一个元数据资源集，同时构建元数据并生成应用一般唯一的的session工厂
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("/hibernate.cfg.xml").build();
        sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }


    /*
        注意事项：
            （1）修改完数据需要，调用session.close()关闭会话，数据才会被持久化到数据库中
            （2）
     */



    // 2、使用 hibernate-api 进行 crud
    /**
     * 2.1、新增数据
     */
    @Test
    public void testSave() {
        Transaction transaction = null;
        // 创建hibernate session对象
        try(Session session = sf.openSession();) {
            // 开启事务
            transaction = session.beginTransaction();

            // 保存数据
            Customer customer = new Customer();
            customer.setCustName("张三");
            customer.setCustAddress("Beijing");
            session.save(customer);

            // 提交事务
            transaction.commit();
        } catch (Exception e) {
            // 事务回滚
            transaction.rollback();
        }
    }

    /**
     * 2.2、查询数据
     */
    @Test
    public void testFind() {
        Transaction transaction = null;
        // 创建hibernate session对象
        try(Session session = sf.openSession();) {
            // 开启事务
            transaction = session.beginTransaction();

            // 查询数据
            // 参数1：实体类对象，参数2：主键，
            Customer customer = session.find(Customer.class, 1L);

            // 打印查询数据
            System.out.println(customer);

            // 提交事务
            transaction.commit();
        } catch (Exception e) {
            // 事务回滚
            transaction.rollback();
        }
    }

    /**
     * 2.3、更新数据
     * merge：
     *  1.没有id，会当新数据插入（请注释掉setCustId，再测试）
     *  2.有id，会先查（请解开注释setCustId，再测试）
     *      有变动，就更新
     *      没变动，不更新
     */
    @Test
    public void testMerge() {
        Transaction transaction = null;
        // 创建hibernate session对象
        try(Session session = sf.openSession();) {
            // 开启事务
            transaction = session.beginTransaction();

            // 更新数据
            Customer customer = new Customer();
            customer.setCustId(1L);
            customer.setCustName("张三");
            customer.setCustAddress("Shanghai1");
            Customer result = (Customer) session.merge(customer);

            // 打印merge返回值
            System.out.println("merge result: customer = " + result);

            // 提交事务
            transaction.commit();
        } catch (Exception e) {
            // 事务回滚
            transaction.rollback();
        }
    }

    /**
     * update：
     *  1.没有id，会抛出异常TransientObjectException（请注释掉setCustId，再测试）
     *  2.有id，无论是否变动，都更新（请解开注释setCustId，再测试）
     */
    @Test
    public void testUpdate() {
        Transaction transaction = null;
        // 创建hibernate session对象
        try(Session session = sf.openSession();) {
            // 开启事务
            transaction = session.beginTransaction();

            // 更新数据
            Customer customer = new Customer();
            customer.setCustId(1L);
            customer.setCustName("张三");
            customer.setCustAddress("Shanghai");
            session.update(customer);

            // 提交事务
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            // 事务回滚
            transaction.rollback();
        }
    }

    /**
     * saveOrUpdate：
     *  1.没有id，会当新数据插入（请注释掉setCustId，再测试）
     *  2.有id，就更新（请解开注释setCustId，再测试）
     */
    @Test
    public void testSaveOrUpdate() {
        Transaction transaction = null;
        // 创建hibernate session对象
        try(Session session = sf.openSession();) {
            // 开启事务
            transaction = session.beginTransaction();

            // 更新数据
            Customer customer = new Customer();
            customer.setCustId(1L);
            customer.setCustName("张三");
            customer.setCustAddress("Beijing");
            session.saveOrUpdate(customer);

            // 提交事务
            transaction.commit();
        } catch (Exception e) {
            // 事务回滚
            transaction.rollback();
        }
    }

    /**
     * 2.4、删除数据
     * 通过id删除
     */
    @Test
    public void testRemove() {
        Transaction transaction = null;
        // 创建hibernate session对象
        try(Session session = sf.openSession();) {
            // 开启事务
            transaction = session.beginTransaction();

            // 删除数据
            Customer customer = new Customer();
            customer.setCustId(1L);
            customer.setCustName("张三");
            customer.setCustAddress("Beijing");
            session.remove(customer);

            // 提交事务
            transaction.commit();
        } catch (Exception e) {
            // 事务回滚
            transaction.rollback();
        }
    }


    // 3、使用 hql
    /**
     * 3.1、简单查询数据
     */
    @Test
    public void testSelectHSQL() {
        Transaction transaction = null;
        // 创建hibernate session对象
        try(Session session = sf.openSession();) {
            // 开启事务
            transaction = session.beginTransaction();

            long id = 2L;
            String hql = "FROM Customer WHERE custId = :id";

            // 查询数据
            List<Customer> customerList = session.createQuery(hql, Customer.class)
                    .setParameter("id", id)
                    .getResultList();

            // 打印数据
            System.out.println("customerList = " + customerList);

            // 提交事务
            transaction.commit();
        } catch (Exception e) {
            // 事务回滚
            transaction.rollback();
        }
    }
}
