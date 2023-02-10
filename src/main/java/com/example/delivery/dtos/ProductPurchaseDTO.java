package com.example.delivery.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProductPurchaseDTO {
    private Long productId;
    private Long accountId;
    private Long productCount;
}
