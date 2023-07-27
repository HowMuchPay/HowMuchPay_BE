package com.example.howmuch.domain.entity;

import com.example.howmuch.constant.RoleType;
import com.example.howmuch.domain.BaseTimeEntity;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "users")
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usr_id")
    private Long id;

    @Column(name = "usr_oauth_id", nullable = false)
    private String oauthId;

    @Column(name = "usr_nickname", nullable = false)
    private String nickname;

    @Column(name = "usr_profile")
    private String profileImage;

    @Column(name = "usr_phone", unique = true)
    private String phoneNumber;

    @Column(name = "usr_total_pay")
    private long userTotalPayAmount;

    @Column(name = "usr_total_rcv")
    private long userTotalReceiveAmount;


    @Enumerated(EnumType.STRING)
    @Column(name = "usr_role", nullable = false)
    private RoleType roleType;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user")
    private FirebaseToken firebaseToken;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MyEvent> myEvents = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AcEvent> acEvents = new ArrayList<>();


    public void addTotalPayAmount(long payAmount) {
        this.userTotalPayAmount += payAmount;
    }

    public void addTotalReceiveAmount(long receiveAmount) {
        this.userTotalReceiveAmount += receiveAmount;
    }

    public void addPhoneNumber(String phone) {
        this.phoneNumber = phone;
    }
}

