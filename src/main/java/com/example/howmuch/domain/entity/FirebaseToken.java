package com.example.howmuch.domain.entity;


import com.example.howmuch.domain.BaseTimeEntity;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

//@Slf4j
//@Getter
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
//@Entity
//@Table(name = "fcm_token")
//public class FirebaseToken extends BaseTimeEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "fcm_id")
//    private Long id;
//
//    @Column(name = "token")
//    private String token;
//
//    @OneToOne
//    @JoinColumn(name = "usr_id")
//    private User user;
//}

@Slf4j
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "fcm_token")
public class FirebaseToken extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fcm_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usr_id")
    private User user;

    @Column(name = "fcm_token")
    private String token;

}