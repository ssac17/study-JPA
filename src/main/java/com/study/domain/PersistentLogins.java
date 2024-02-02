package com.study.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Table(name = "persistent_logins")
@Entity
@Getter @Setter
public class PersistentLogins {

    @Id
    @Column(length = 64)
    private String series;

    @Id
    @Column(nullable = false, length = 64)
    private String username;

    @Id
    @Column(nullable = false, length = 64)
    private String token;

    @Id
    @Column(name = "last_used", nullable = false, length = 64)
    private LocalDate lastUsed;

}
