package com.bikkadit.electronic.store.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product extends CustomFields {

    @Id
    private String productId;

    @Column(name = "title")
    private String title;

    @Column(name = "description",length = 10000)
    private String description;

    @Column(name = "price")
    private Double price;

    @Column(name = "discountedPrice")
    private Double discountedPrice;

    @Column(name = "quantity")
    private Long quantity;

    @Column(name = "live")
    private Boolean live;

    @Column(name = "stock")
    private Boolean stock;
}
