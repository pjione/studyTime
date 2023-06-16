package com.studytime.domain.user;

import com.studytime.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    private String userAccount;
    private String password;
    private String name;
    @Enumerated(value = EnumType.STRING)
    private Gender gender;
    private String phone;

    @Builder
    public User(String userAccount, String password, String name, Gender gender, String phone) {
        this.userAccount = userAccount;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.phone = phone;
    }
}
