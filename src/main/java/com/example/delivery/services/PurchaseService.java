package com.example.delivery.services;

import com.example.delivery.dtos.ProductPurchaseDTO;
import com.example.delivery.enums.Status;
import com.example.delivery.models.Order;
import com.example.delivery.models.Product;
import com.example.delivery.models.User;
import com.example.delivery.repositores.OrderRepository;
import com.example.delivery.repositores.ProductRepository;
import com.example.delivery.repositores.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class PurchaseService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public PurchaseService(ProductRepository productRepository,
                           OrderRepository orderRepository,
                           UserRepository userRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Order processPurchase(ProductPurchaseDTO purchaseDTO) {
        Long productId = purchaseDTO.getProductId();
        Long productCount = purchaseDTO.getProductCount();
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("product not found"));
        User user = userRepository.findById(purchaseDTO.getAccountId())
                .orElseThrow(() -> new EntityNotFoundException("user not found"));
        product.setCount(product.getCount() - productCount);
        Product updatedProduct = productRepository.save(product);
        Order order = Order.builder()
                .status(Status.PENDING)
                .productCount(productCount)
                .totalPrice(calculateTotalPrice(productCount, product.getPrice()))
                .user(user)
                .product(updatedProduct)
                .build();

        return orderRepository.save(order);
    }

    private Double calculateTotalPrice(Long productCount, Double price) {
        return price * productCount;
    }
}
