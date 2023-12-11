package com.study.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter @Setter
/*
 * @EqualsAndHashCode
 * equals()와 hashCode() 메소드를 자동으로 생성해주는 역할
 * equals() 메소드는 두 객체의 내용이 같은지 비교하는데 사용되고,
 * hashCode() 메소드는 객체를 식별할 수 있는 정수 값을 반환하는 데 사용
 * */
@EqualsAndHashCode(of = "id")
@Builder @NoArgsConstructor @AllArgsConstructor
public class Account {

    @Id @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String nickname;

    private String password;

    private boolean emailVerified;

    private String emailCheckToken;

    private LocalDateTime joinedAt;

    private String bio;

    private String url;

    private String occupation;

    private String location;

    /*
    * @Lob
    * 문자열의 크기가 일반적인 VARCHAR(255)를 초과하는 경우나,바이트 배열이 필요한 경우에 사용
    * @Lob 어노테이션이 붙은 필드는 자동으로 CLOB(문자 대용량 객체) 또는 BLOB(바이너리 대용량 객체) 타입으로 매핑
    * */
    @Lob @Basic(fetch = FetchType.EAGER)
    private String profileImage;

    private boolean studyCreateByEmail;

    private boolean studyCreateByWeb;

    private boolean studyEnrollmentResultByEmail;

    private boolean studyEnrollmentResultByWeb;

    private boolean studyUpdateByEmail;

    private boolean studyUpdateByWeb;

    public void generateAEamilCheckToken() {
        this.emailCheckToken = UUID.randomUUID().toString();
    }
}
