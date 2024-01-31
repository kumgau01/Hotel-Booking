package com.mvp.hotel.booking.mvp_hotel_booking.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(value = {"createdDate", "lastModifiedDate"})
@MappedSuperclass
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditableEntity extends BaseEntity implements Serializable {
	@CreatedDate
    @Column(name = "created_date", columnDefinition = "TIMESTAMP", updatable = false)
    protected LocalDateTime createdDate;

    @LastModifiedDate
    @Column(name = "last_modified_date",columnDefinition = "TIMESTAMP") // if (..., nullable = false) than org.hibernate.PropertyValueException: not-null property references a null or transient value : dev.marioszocs.hotelbookingapi.entity.Hotel.lastModifiedDate
    private LocalDateTime lastModifiedDate;

}
