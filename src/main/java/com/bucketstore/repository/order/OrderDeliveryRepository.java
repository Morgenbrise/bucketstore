package com.bucketstore.repository.order;

import com.bucketstore.domain.OrderDelivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDeliveryRepository extends JpaRepository<OrderDelivery, Long> {
}
