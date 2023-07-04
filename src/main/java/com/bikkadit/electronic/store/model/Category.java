package com.bikkadit.electronic.store.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category extends CustomFields{

    @Id
    private String categoryId;

    @Column(name = "category_title",length = 50)
    private String title;

    @Column(name = "category_desc",length = 60)
    private String description;

    private String coverImage;

}
