import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.SessionFactoryBuilder;
import org.hibernate.boot.internal.SessionFactoryBuilderImpl;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.mapping.MetadataSource;
import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * StandardServiceRegistry 作用
 * MetadataSources 作用
 */
public class HibernateTest {

    private SessionFactory sessionFactory;

    // 1、创建SessionFactory
    @Before
    void init() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        sessionFactory = new MetadataSources().buildMetadata(registry).buildSessionFactory();
    }

    // 2、测试
    void testSave() {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();



            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
    }
}
