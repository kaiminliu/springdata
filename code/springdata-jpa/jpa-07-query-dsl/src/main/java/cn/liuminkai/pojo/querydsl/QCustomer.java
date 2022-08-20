package cn.liuminkai.pojo.querydsl;

import static com.querydsl.core.types.PathMetadataFactory.*;

import cn.liuminkai.pojo.Customer;
import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;

/**
 * QCustomer is a Querydsl query type for Customer
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCustomer extends EntityPathBase<Customer> {

    private static final long serialVersionUID = 663024720L;

    public static final QCustomer customer = new QCustomer("customer");

    public final StringPath custAddress = createString("custAddress");

    public final NumberPath<Long> custId = createNumber("custId", Long.class);

    public final StringPath custName = createString("custName");

    public final BooleanPath dataType = createBoolean("dataType");

    public QCustomer(String variable) {
        super(Customer.class, forVariable(variable));
    }

    public QCustomer(Path<? extends Customer> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCustomer(PathMetadata metadata) {
        super(Customer.class, metadata);
    }

}