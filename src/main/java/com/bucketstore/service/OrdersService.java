package com.bucketstore.service;

import com.bucketstore.domain.OrderItem;
import com.bucketstore.domain.Orders;
import com.bucketstore.domain.Product;
import com.bucketstore.domain.Users;
import com.bucketstore.enums.OrderStatus;
import com.bucketstore.repository.order.OrdersRepository;
import com.bucketstore.repository.orderItem.OrderItemRepository;
import com.bucketstore.repository.product.ProductRepository;
import com.bucketstore.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrdersService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrdersRepository ordersRepository;
    private final OrderItemRepository orderItemRepository;

    public void createOrder(List<String> productCodes) {
        List<Product> products = productRepository.findByProductCodeIn(productCodes);

        if (products.size() != productCodes.size()) {
            throw new IllegalArgumentException("유효하지 않은 상품 코드가 포함되어 있습니다.");
        }

        // 임시 유저 (id=1)
        Users users = userRepository.findById(1L)
                .orElseThrow(() -> new IllegalStateException("유저가 존재하지 않습니다."));

        Orders order = Orders.builder()
                .orderCode(UUID.randomUUID().toString())
                .orderStatus(OrderStatus.PENDING.name())
                .users(users)
                .orderDate(LocalDateTime.now())
                .totalPrice(products.stream().mapToInt(Product::getBasePrice).sum())
                .deliveryFee(0)
                .build();

        ordersRepository.save(order);

        for (Product product : products) {
            OrderItem item = OrderItem.builder()
                    .order(order)
                    .product(product)
                    .option(null) // 추후 옵션 선택 기능 추가 시 적용
                    .quantity(1)
                    .itemPrice(product.getBasePrice())
                    .build();

            orderItemRepository.save(item);
        }

    }

}
