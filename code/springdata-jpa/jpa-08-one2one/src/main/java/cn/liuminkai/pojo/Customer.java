package cn.liuminkai.pojo;

import cn.liuminkai.pojo.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/***
 * @Author 徐庶   QQ:1092002729
 * @Slogan 致敬大师，致敬未来的你
 */
@Entity     // 作为hibernate 实体类
@Table(name = "tb_customer")       // 映射的表明
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long custId; //客户的主键

    @Column(name = "cust_name")
    private String custName;//客户名称

    @Column(name="cust_address")
    private String custAddress;//客户地址


    // 单向关联  一对一
    /**
    * cascade 设置关联操作
    *    ALL,       所有持久化操作
        PERSIST     只有插入才会执行关联操作
        MERGE,      只有修改才会执行关联操作
        REMOVE,     只有删除才会执行关联操作
      fetch 设置是否懒加载
          EAGER 立即加载（默认）
          LAZY 懒加载（ 直到用到对象才会进行查询，因为不是所有的关联对象 都需要用到）
      orphanRemoval  关联移除（通常在修改的时候会用到）
          一旦把关联的数据设置null ，或者修改为其他的关联数据， 如果想删除关联数据， 就可以设置true
      optional  限制关联的对象不能为null
            true 可以为null(默认 ) false 不能为null
      mappedBy  将外键约束执行另一方维护(通常在双向关联关系中，会放弃一方的外键约束）
        值= 另一方关联属性名
    **/
    /*
        测试用例
        1.测试OneToOne的属性
            1.1 cascade
                默认情况下的增删改查，结果（语句情况，是否异常）
                其他情况下
            1.2 fetch 测试LAZY （cascade设置为ALL）
            1.3 orphanRemoval （更新的时候测试 + 删除的时候测试）
            1.4 optional 忘记啥作用了，重新看一下
            1.5 mappedBy twoWay时查看
            1.6 twoWay
                1.6.1 查看增删改查
                1.6.2 查看增删改查+mappedBy
        2.测试OneToMany
            2.1 测试增删改查
            2.2 查看效果 增 删 与 ManyToOne 的适合度
        3.测试ManyToOne
            3.1 twoWay
                3.1.1 测试增删改查
                3.1.2 测试增删改查+mappedBy
            3.2 查看效果 增 删 与 OneToMany 的适合度
        4.测试OneToMany和ManyToOne
            4.1 oneWay 测试增删改查
            4.2 twoWay
                4.2.1 测试增删改查
                4.2.2 测试增删改查+mappedBy
        5.测试ManyToMany
            5.1 oneWay 测试增删改查
            5.2 twoWay
                5.2.1 测试增删改查
                5.2.2 测试增删改查+mappedBy
        6.测试乐观锁
        7.测试审计
            1.框架提供
            2.自定义
     */



    // 单向关联，请解开如下注释
    @OneToOne(cascade = CascadeType.ALL)
    // 设置外键的字段名
    @JoinColumn(name="account_id")
    private Account account;


    /**
     // 双向关系，请解开该注释内容
     @OneToOne(cascade = CascadeType.ALL)
     private Account account;
     */
}