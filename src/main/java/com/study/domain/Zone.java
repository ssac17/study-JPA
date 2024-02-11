package com.study.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter @Setter
@EqualsAndHashCode()
@Builder @NoArgsConstructor @AllArgsConstructor
public class Zone {

    @Id
    @GeneratedValue
    private Long Id;

    @Column(nullable = false)
    private String city;

    private String localName;


    @Override
    public String toString() {
        return String.format("%s(%s)", localName, city);
    }
}
