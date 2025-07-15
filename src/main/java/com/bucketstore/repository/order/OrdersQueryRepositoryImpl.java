package com.bucketstore.repository.order;

import com.bucketstore.common.utils.PageRequestUtils;
import com.bucketstore.domain.QOrders;
import com.bucketstore.domain.QUsers;
import com.bucketstore.dto.order.OrderResponse;
import com.bucketstore.dto.order.OrderSearchRequest;
import com.bucketstore.enums.sort.OrderSort;
import com.bucketstore.common.enums.SortCondition;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrdersQueryRepositoryImpl implements OrdersQueryRepository {

    private final JPAQueryFactory queryFactory;

    public OrdersQueryRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<OrderResponse> findOrders(OrderSearchRequest request) {
        QOrders orders = QOrders.orders;
        QUsers users = QUsers.users;

        Pageable pageable = PageRequestUtils.of(request.page(), request.size());

        List<SortCondition> conditions = request.sort().stream()
                .map(s -> new SortCondition(OrderSort.from(s.getCode()), s.getDirection()))
                .toList();

        return queryFactory
                .select(Projections.constructor(OrderResponse.class,
                        orders.orderCode,
                        orders.orderStatus.stringValue(),
                        orders.totalPrice,
                        orders.deliveryFee,
                        orders.orderDate
                ))
                .from(orders)
                .leftJoin(orders.users, users)
                .orderBy(
                        conditions.stream()
                        .map(sc -> sc.field().toOrderSpecifier(sc.direction()))
                        .toArray(OrderSpecifier[]::new)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }
}
