package com.bikkadit.electronic.store.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category extends CustomFields {

    @Id
    private String categoryId;

    @Column(name = "category_title", length = 50)
    private String title;

    @Column(name = "category_desc", length = 60)
    private String description;

    private String coverImage;

    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Product> products = new ArrayList<>();

}
