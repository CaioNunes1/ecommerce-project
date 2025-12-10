package com.caio.ecommerce_project.business;

import com.caio.ecommerce_project.dto.CreatedOrderRequest;
import com.caio.ecommerce_project.dto.OrderItemDto;
import com.caio.ecommerce_project.infraestructure.entitys.Order;
import com.caio.ecommerce_project.infraestructure.entitys.OrderItem;
import com.caio.ecommerce_project.infraestructure.entitys.Product;
import com.caio.ecommerce_project.infraestructure.entitys.User;
import com.caio.ecommerce_project.infraestructure.repositorys.OrderRepository;
import com.caio.ecommerce_project.infraestructure.repositorys.ProductRepository;
import com.caio.ecommerce_project.infraestructure.repositorys.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Transactional
    public Order createOrder(CreatedOrderRequest request){
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(()-> new RuntimeException("usuario não encontrado"));

        Order order = Order.builder()
                .user(user)
                .status("PENDING")
                .createdAt(Instant.now())
                .build();

        BigDecimal total = BigDecimal.ZERO;

        for (OrderItemDto itemDto : request.getItems()){
            Product product = productRepository.findById(itemDto.getProductId())
                    .orElseThrow(()-> new RuntimeException("Produto não encontrado"));

            BigDecimal price = product.getPrice();
            int qty = itemDto.getQuantity() == null ? 1 : itemDto.getQuantity();
            BigDecimal line = price.multiply(BigDecimal.valueOf(qty));
            total = total.add(line);

            OrderItem item = OrderItem.builder()
                    .order(order)
                    .product(product)
                    .quantity(qty)
                    .priceAtPurchase(price)
                    .build();

            order.getItems().add(item);
        }
        order.setTotal(total);
        return orderRepository.saveAndFlush(order);

    }
    public Order findById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
    }
}
