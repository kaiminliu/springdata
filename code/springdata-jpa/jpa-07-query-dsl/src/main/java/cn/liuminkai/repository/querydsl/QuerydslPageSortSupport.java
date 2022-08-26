package cn.liuminkai.repository.querydsl;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.querydsl.QSort;
import org.springframework.data.repository.support.PageableExecutionUtils;

import java.util.List;

public abstract class QuerydslPageSortSupport extends QuerydslRepositorySupport {

    public QuerydslPageSortSupport(Class<?> domainClass) {
        super(domainClass);
    }

    protected <T> Page<T> page(JPQLQuery<T> query, Pageable pageable) {
        List<T> content = this.getQuerydsl().applyPagination(pageable, query).fetch();
        query.getClass();
        return PageableExecutionUtils.getPage(content, pageable, query::fetchCount);
    }

    protected <T> List<T> sort(JPQLQuery<T> query, Sort sort) {
        return this.getQuerydsl().applySorting(sort, query).fetch();
    }

    protected <T> List<T> sort(JPQLQuery<T> query, OrderSpecifier<?>... orders) {
        return this.sort(query, (Sort)(new QSort(orders)));
    }
}