package com.bikkadit.electronic.store.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@MappedSuperclass
public class CustomFields {

    @Column(name = "is_active_switch")
    private String isActive;

    @Column(name = "created_by")
    @CreatedBy
    private String createdBy;

    @Column(name = "create_date",updatable = false)
    @CreationTimestamp
    private LocalDateTime createdOn;

    @Column(name = "modified_by")
    @LastModifiedBy
    private String modifiedBy;

    @Column(name = "modified_on",updatable = false)
    @UpdateTimestamp
    private LocalDateTime modifiedOn;

}
