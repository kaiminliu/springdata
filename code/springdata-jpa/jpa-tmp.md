jpa学习分为三个模块
- spring：spring-data-jpa
- springboot：springboot-jpa
- jpa-hibernate
# 一、什么是JPA

JPA仅仅是一种规范，也就是说JPA仅仅定义了一些接口，而接口是需要实现才能工作的。

JPA与JDBC区别
- JPA实现由全自动ORM框架
- JDBC实现 各数据库厂商

Eclipse基金会，Jakart Persistence API

JPA规范为我们提供了：

（1）ORM映射元数据
    如：@Entity、@Table、@Id、@Column等注解

（2）JPA的API
    如：entityManager.merge(T t);

（3）JPQL查询语言
    如：`from Student s where s.name = ?1`

# 二、Hibernate与JPA
Hibernate是一个ORM框架，是JPA的实现

Hibernate与Mybatis简单对比
- mybatis: 小巧，方便，高效，简单，直接，半自动
  - 小巧：mybatis就是jdbc封装
  - 国内流行
  - 应用场景：在业务比较复杂系统进行使用
- hibernate: 强大，方便，高效，复杂，绕弯，全自动
  - 强大：根据ORM映射生成不同的SQL
  - 国外流行
  - 应用场景：在业务相对简单的系统进行使用（现在学习，是因为，随着微服务的流行，系统会被拆分为一个个小服务，小服务往往不会太复杂）

hibernate 示例

pom.xml

实体类
code first ：不需要创建表，只需要关心pojo类，当时需要创建数据库

配置文件

创建数据库

测试(API使用)
1、创建 SessionFactory
StandardServiceRegistryBuilder
MetadataSources

test1
sf.openSession
s.beginT
tx.commit

save
saveOrUpdate 有id更新，无id吃ARU
find
update
remove

测试(hql使用) hql 与 jpql 区别
session.createQuery.getResultList

jpa集成hibernate
添加 META—INF.persistence.xml

测试（JPA使用）
Persistence.createEnMF

persist
find（立即查询）
getRef（延迟查询）
merge （saveOrUpdate，只更新可以用jpql）
remove （直接删除，报删除游离状态异常，只能删除持久化状态（从数据库中查出的状态就是持久化状态））

JPQL
em.createQuery
    使用sql语句
        createNativeQuery

实现切换为openJpa
1.引入依赖
2.


jpa 4种状态
- 临时
- 持久，对持久状态的更改会对数据库进行同步
  - 只要修改，提交就会，被持久化，即使是find
  - 1. find -> 修改实体 -> commit
  - 2. find -> remove -> persist -> commit 不懂：p9 7:00
  - 
- 删除
- 游离

一级缓存 基于EntityManager 
二级缓存



spring-data-jpa 简化jpa的框架
1、引入依赖

2、配置
2.1、xml
EntityManagerFactory
TransationManager

2.2、测试类测试

repo.delete(customer); 底层会帮我们先查询一下（游离=>持久），再删除

2.2、javaConfig


3、repository api的使用
CrudRepository
PagingAndSortRepository
    PageRequest.
    Sort. 
        两种：字符串硬编码、type-safe

4、自定义持久化操作（复杂）
4.1、JPQL （@Query）
- 可以自由设置返回值，返回单挑记录使用pojo类，多条记录使用list
- 查询可以使用 
    - ?数字
    - :参数名
- 增删改，需要加声明式事务@Transaction（通常放在业务逻辑层） + @Modifying, 否则报错
- JPQL不支持新增，但他的实现Hibernate支持，伪新增(insert into ... select)，可以插入从别的地方查出的
    - 我认为直接使用 SQL不久得了
测试
-   提示插件 jpabuddy 好像已经过期了

4.2、SQL（@Query(nativeQuery=true)）


4.3、规定方法名
findByXxx



Like 需要自己拼上百分号


4.4、动态条件查询（多条件查询，有值就加入到查询条件，没有就不参与查询）
4.4.1、QueryByExample
- 字符串
withIgnorePaths 忽略某个条件
withIgnoreCase 会使用lower函数
withStringMatcher 对所有字符串property进行匹配
withMatcher（静态方法【支持链式写法】或lambda表达式） 对指定字符串property进行匹配
使用 withMatcher时 withIgnoreCase 会失效 ？？？ p17 26:00
  
4.4.2、Specifications（很复杂）
new Specifications(root, query, builder);

不支持分组，就是设置了，底层是固定的，这个分组也是无效的
不支持查询指定字段，底层会设置死 em， 但是存在线程安全问题 不能使用Autowired，要用@PersistenceContext，一个线程绑定一个em对象

@PersistenceContext 原理和作用，以及与Autowired Resource 区别
https://www.1024sky.cn/blog/article/539

还可以使用 em.getCriteBuilder，builder.createQuery query.form


4.4.3、Querydsl
可读性更好
1、集成接口
2、映入依赖
插件生成Q类
BooleanBuilder
Q类.类.xx
不可以可以分组、指定字段 需要用 em， 但是存在线程安全问题 不能使用Autowired，要用@PersistenceContext，一个线程绑定一个em对象

@PersistenceContext 原理和作用，以及与Autowired Resource 区别
https://www.1024sky.cn/blog/article/539

JPAQueryFactory方式

集成QuerydslPageSortSupport from()方式
fetchJoin


5、多表关联操作（hibernate实现）
说明去：hibernate官网看
引入 lombok
mybatis
    - 写sql
mybatis-plus
    - 写sql
jpa
    - 注解
        - 获取多表数据
        - 插入多表数据
        - 删掉关联数据
5.1、OneToOne
5.1.1、 单向关联
@OneToOne
    cascade维护关联操作，配置关联操作后，在主表crud时，子表才会跟着crud
    fetch 查询类型，立即加载（默认） or 懒加载（用到才查，提升查询效率）
    orphanRemoval 关联删除（更新的时候），当为ture时，只要将主表外键设置为null，子表就会删除对应的记录
    targetEntity 关联的目标类型（默认根据关联类型去设置，一般我们不用管）
    optional 是否可以为null 
    
@JoinColumn 维护外键
    name 外键名字（数据库中的）

疑问：orphanRemoval 和 cascade维护关联delete 是一样的吗

注意：配置了懒加载需要在调用方法上加@Transational，为什么呢？ 看OneToOneTest
tb_customer(account) 1<-->1 tb_account

5.1.2、双向关联 （可以再看一边）
无法再进行关联删除，如何删除，将一方外键设置为null，再删除，jpa已经帮我们封装好了
两次insert+一次update
@OneToOne
    cascade维护关联操作，配置关联操作后，在主表crud时，子表才会跟着crud
    fetch 查询类型，立即加载（默认） or 懒加载（用到才查，提升查询效率）
    orphanRemoval 关联删除（更新的时候），当为ture时，只要将主表外键设置为null，子表就会删除对应的记录
    targetEntity 关联的目标类型（默认根据关联类型去设置，一般我们不用管）
    optional 是否可以为null
    mappedBy 将外键约束到另一方维护，另一方的关联属性名 （插入和删除都不会有更新的那一步了）

@JoinColumn 维护外键
    name 外键名字（数据库中的）


5.2、OneToMany
外键维护在多的一方
tb_customer(list<message>)  1<-->m tb_message
关联插入：三个插入 + 两个更新
关联查询（适合）：

5.3、ManyToOne
外键维护在多的一方
tb_customer  1<-->m tb_message(customer)
关联插入（适合）：三个插入
关联查询：需要自定义查询（5种），规定方法名最合适（但是只能通过关联对象的id进行匹配，其他属性是无效的）

5.4、ManyToMany
@ManyToMany
@JoinTable 可以不写，自动生成
    name 中间表名字
    joinColumns 设置这边的外键名
    inverseJoinColumns 设置关联表的外键名

5.4.1、单向
@Commit ??? 单元测试，ManyToManyTest 

多对多不适合删除

6、乐观锁（hibernate）
@Version
防止并发修改

7、审计
①实现AuditorAware
②监听
③开启功能

8、springdata repository原理
原理+手写源码+画图

原理：
①spring 提供了 CustomRepository 的一个jpa-repositories统一实现类RepositoryImpl（SimpleJpaRepository）
②通过动态代理会根据 CustomRepository 的类加载器，接口s，iHandler 生成代理对象CustomRepositoryProxy（JdkDynamicAopProxy）
    在iHandler中，CustomRepositoryProxy的方法中会调用 RepositoryImpl 对应方法
        RepositoryImpl 会根据构造器传递进来的 em ， pojoClass， 使用 jpa-api 完成相应的数据库操作
            em 的获取是通过 iocContext 获取的 EntityManagerFactory（LocalContainerEntityManagerFactoryBean） 创建 EntityManager
            pojoClass 的获取是通过 CustomRepositoryProxy中利用 反射 获取 CustomRepository 的父接口上的泛型参数得知

9、spring整合jpa原理

11、springboot-jpa

命名策略的配置，主要看是否是先创建数据库，还是先写代码
    - 如果是先创建数据库，再写代码，是有必要更改命名策略的
    - 如果是先写代码，再自动生成数据库，就没必要管命名策略
第一阶段是从域模型映射中确定正确的逻辑名称。逻辑名称可以由用户显式指定（例如，使用@Columnor @Table），也可以由 Hibernate 通过 ImplicitNamingStrategy契约隐式确定
其次是将此逻辑名称解析为由PhysicalNamingStrategy合同定义的物理名称。

@Column等 显示命名
ImplicitNamingStrategy 隐式命名策略，未显式就会使用隐式，有显式就不会使用隐式，一般就是驼峰转下划线
PhysicalNamingStrategy 物理命名策略 对显示命名或隐式命名之后的结果进行处理，一般是单词全拼转缩写等等

源码
JpaBaseConfiguration
JpaRepositoriesAutoConfiguration  @Import(JpaRepositoriesAutoConfigureRegistrar.class)

注解全
注解在上面各个阶段都生效吗
First1 JPA 在mysql下生效吗
单标查询ok
多表查询ok
单标动态查询ok
多表动态查询 可以
Querydsl https://blog.csdn.net/wjw465150/article/details/124879048
JPQL
HQL
SQL

看别人博客再行补充
JPA-Hibernate-JDBC 与 MyBatis—JDBC 对比


B站 JPA评论
赞
1.jpa如果微服务项目不创建表关联的话贼方便 也不需要 关联注解 如果单体表关联那种 直接注解控制关联 设置级联 随便查
    - 实际生产里关联关系一大堆，用起来是真滴烦人，在动态查询和关联关系以及sql 优化上来说，我个人的jpa 开发体验甚至不如jdbcTemplate
客观
1.DDD，CQRS读写分离，两者结合，jpa重在领域建模，读操作复杂查询用mybatis
2.说实话国内mybatis绝对不会过时，因为灵活。看着jpa很美，但是太完备的东西灵活度就差，稍微想动一动就很要命。我新起的项目基本已经完全放弃jpa了，mybatis真的香。不过学一下jpa也有点用，至少面试的时候有点用
3.两种orm技术，现在都能无缝切换，用哪个都可以，架构师想要的样子，我会

怼
1.这玩意就是把写个SQL解决的问题弄复杂，映射完了生成的SQL是不可控，会出现效率问题。99%以上情况不会换数据库，如果换数据库，改代码那点工作根本不算啥，整个运维体系，历史数据导入等等一系列问题，这就导致换数据库情况根本不存在。jpa可以不写SQL，呵呵，不会SQL根本找不到工作
2.为什么国外喜欢用全自动的ORM框架，而国内喜欢用MyBatis这种半自动呢？技术都是跟着大厂走，国内大厂面对的数据往往是上亿的，全自动ORM框架做SQL优化就非常困难了，而看起来相对繁琐的MyBatis自定义SQL反而更方便。
3.看完之后，觉得真的是难用，不明白有些公司为啥用这个，性能不高，开发效率也不高，学习成本还高.mybatis+mybatis-plus  不香嘛，不仅写起来简单，而且jpa里边的那些find啥的默认方法，mybatis-puls都有，还特好用，开发起来也简单。单表增删查改也有默认实现，多表的话jdbc也比jpa好用。所有来个大佬给解释一下为什么？
    - 可能唯一的好处就是换数据库方便吧
        - :就因为关联一大堆才舒服方便啊 级联跟注解关联简直爽死 一大堆关联查询数据 查一下本表关联表要的数据也出来了直接用json注解控制序列化 如果用sql要写一大堆 但是jpa注解控制 啥也不用写查也方便
        - 感觉多表联查不方便绝对是关联注解设置有问题 我以前也这么感觉 但是用久了就发现规律了 以前我也jpa里用jdbctemplate 后来根本不需要 要写原生sql肯定是设计不合理
4.mybatis灵活度更好吧
5.国外那用户量能和国内比吗。。。
人家也不需要秒杀什么的活动，人家是零元购。所以开发的框架一个比一个简单粗暴，但是效率不一定是最好的，重在好上手。
6.得考虑学习成本，mybatis几乎零入门门槛，jpa不学一段时间，真的不能碰生产环境代码

hibernate 官方文档疑问：
想oneToOne这些注解（映射）可以作用在 @Query，Specification、QueryDsl、MethodName、QueryByExample 上吗
Hibernate 提供了许多内置的基本类型
@Temporal java.util Or java.time 下面的包需要显示映射，不用会有啥结果
SQL 引用标识符 Hibernate使用` ,Jpa使用 \"
@Entity的name有什么用
@Subselect 是否支持 JPQL或HQL查询
2.5.9。定义自定义实体代理
2.5.10。使用 @Tuplizer 注释的动态实体代理
2.5.11。定义一个自定义实体持久化器
@Formula 会不会持久化
2.5.12。访问策略 ？？？ 基于字段的访问 不用写Getter为啥
字段 和 属性 有啥区别
@Embedded + @Access = @Embeddable ？
@EmbeddedId/@IdClass + @ManyToOne 不支持移植？？ 为什么
非聚合复合标识符？？
2.6.5。具有关联的复合标识符
2.6.6。具有生成属性的复合标识符
2.6.9。使用序列
2.6.10。使用 IDENTITY 列
2.6.10。使用 IDENTITY 列
2.6.12。使用 UUID 生成  这里只是强调一些UUID类型可以直接作为主键
2.6.13。优化器
2.6.14。使用@GenericGenerator
2.6.15。派生标识符
2.6.16。@RowId

注解：
@Entity
@Table
@Id
@Embeddable（jpa中没有用处） 修饰类 表示此类可以被插入某个entity中
@Basic 标注基本类型和其包装类型，可省略
@Column
@NaturalId 唯一键
@Synchronize
注解-类型映射：
    @org.hibernate.annotations.Type( type = "nstring" ) 在实体属性JavaType上指定HibernateType
    √ @Enumerated 映射枚举
    √ @Convert 指定属性转换器
    @Lob
    @Nationalized ？？？
    UUID映射 ？？？
    √ @Temporal(TemporalType.DATE)
    √ @Generated 属性值生成时间
    √ @GeneratorType 指定自定义属性值生成
    @CreationTimestamp，@UpdateTimestamp 持久化时，设置为JVM当前时间错
    @ValueGenerationType 自定义 @Generated or @CreationTimestamp 属性值生成器
    √ @ColumnTransformer 列转换器
    √ @Formula 虚拟列
    √ @Embeddable 可嵌入类型
    √ @Embedded ？？？
    @AttributeOverride 基本参数名重写，主要是属性名冲突，例如多个相同可嵌入类型是使用
    @AssociationOverride 引用参数名重写
    @Target 相当于 @XToX的 targetEntity属性 指定关联接口的实现类
    @Parent 类似于 @OneToOne双向 外键维护在@Parent端
    @Entity JPA实体类 参考 POJO 模型
    @Table 映射表的catalog、scheme、name ？？？？  
    equals and hashCode 没细看
    √ @Subselect 实体映射到 SQL 查询
    √ @Synchronize 指定 @Subselect查询过程中需要哪些表，因为Hibernate 无法解析原生SQL
    @Transient 避免持久化
    @Access 修改访问策略
    @ElementCollection  可嵌入集合的实体 @CollectionTable
    @Id 主键
    @GeneratedValue 主键生成策略
    @GenerationType
    @EmbeddedId 复合主键
    @IdClass    复合主键