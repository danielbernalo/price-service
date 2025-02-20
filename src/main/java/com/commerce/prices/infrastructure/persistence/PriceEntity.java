package com.commerce.prices.infrastructure.persistence;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Table("prices")
@NoArgsConstructor
public class PriceEntity {
    @Id
    private Long id;

    @Column("brand_id")

    private Long brandId;

    @Column("start_date")
    private LocalDateTime startDate;

    @Column("end_date")
    private LocalDateTime endDate;

    @Column("price_list")
    private Long priceList;

    @Column("product_id")
    private Long productId;

    @Column("priority")
    private Integer priority;

    @Column("price")
    private BigDecimal price;

    @Column("curr")
    private String currency;
}