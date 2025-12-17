package com.caio.ecommerce_project.business;

import com.caio.ecommerce_project.dto.CreatedOrderRequest;
import com.caio.ecommerce_project.dto.OrderItemDto;
import com.caio.ecommerce_project.infraestructure.entitys.*;
import com.caio.ecommerce_project.infraestructure.repositorys.OrderRepository;
import com.caio.ecommerce_project.infraestructure.repositorys.ProductRepository;
import com.caio.ecommerce_project.infraestructure.repositorys.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Transactional
    public Order createOrder(CreatedOrderRequest request){
        // valida existência do usuário
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("usuario não encontrado: id=" + request.getUserId()));

        // valida items não nulos
        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new IllegalArgumentException("Pedido deve conter ao menos um item");
        }

        // 1) Agregar quantidades por productId (evita linhas duplicadas se cliente enviar duas vezes o mesmo produto)
        Map<Long, Integer> qtyByProduct = new LinkedHashMap<>();
        for (OrderItemDto it : request.getItems()) {
            if (it.getProductId() == null) {
                throw new IllegalArgumentException("productId é obrigatório em cada item");
            }
            int q = it.getQuantity() == null ? 1 : it.getQuantity();
            qtyByProduct.merge(it.getProductId(), q, Integer::sum);
        }

        // 2) Buscar todos os produtos de uma vez (evita N queries e permite detectar faltantes)
        List<Long> productIds = new ArrayList<>(qtyByProduct.keySet());
        List<Product> products = productRepository.findAllById(productIds);

        Set<Long> foundIds = products.stream().map(Product::getId).collect(Collectors.toSet());
        List<Long> missing = productIds.stream().filter(id -> !foundIds.contains(id)).collect(Collectors.toList());
        if (!missing.isEmpty()) {
            throw new RuntimeException("Produtos não encontrados: " + missing);
        }

        // 3) Map productId -> Product
        Map<Long, Product> productById = products.stream()
                .collect(Collectors.toMap(Product::getId, p -> p));

        // 4) Montar order e orderItems
        Order order = Order.builder()
                .user(user)
                .status("PENDING")
                .createdAt(Instant.now())
                .build();

        BigDecimal total = BigDecimal.ZERO;
        for (Map.Entry<Long, Integer> e : qtyByProduct.entrySet()) {
            Long pid = e.getKey();
            Integer qty = e.getValue();
            Product product = productById.get(pid);

            BigDecimal price = product.getPrice();
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

        // 5) salvar (cascade vai persistir os items)
        return orderRepository.saveAndFlush(order);
    }

    public Order findById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
    }
}
