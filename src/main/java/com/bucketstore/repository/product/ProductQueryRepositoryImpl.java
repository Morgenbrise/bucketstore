package com.bucketstore.repository.product;

import com.bucketstore.common.utils.PageRequestUtils;
import com.bucketstore.domain.QProduct;
import com.bucketstore.dto.product.ProductDTO;
import com.bucketstore.dto.product.ProductSearchRequest;
import com.bucketstore.enums.sort.ProductSort;
import com.bucketstore.common.dto.ResolvedSortCondition;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductQueryRepositoryImpl implements ProductQueryRepository {

    private final JPAQueryFactory queryFactory;

    public ProductQueryRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<ProductDTO> findProducts(ProductSearchRequest request) {
        QProduct product = QProduct.product;

        Pageable pageable = PageRequestUtils.of(request.getPage(), request.getSize());

        List<ResolvedSortCondition> conditions = request.getSort().stream()
                .map(s -> new ResolvedSortCondition(ProductSort.from(s.getCode()), s.getDirection()))
                .toList();

        // 쿼리
        OrderSpecifier[] array = conditions.stream()
                .map(sc -> sc.field().toOrderSpecifier(sc.direction()))
                .toArray(OrderSpecifier[]::new);

        return queryFactory
                .select(Projections.constructor(ProductDTO.class,
                        product.productCode,
                        product.productName,
                        product.category,
                        product.brand,
                        product.basePrice,
                        product.description
                ))
                .from(product)
                .orderBy(
                        array
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }
}
