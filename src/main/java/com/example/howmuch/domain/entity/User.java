package com.example.howmuch.domain.entity;

import com.example.howmuch.domain.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@Slf4j
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

    @Column(name = "usr_total_pay")
    private long userTotalPayAmount;

    @Column(name = "usr_total_rcv")
    private long userTotalReceiveAmount;

    // User와의 매핑 설정
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user")
    private FirebaseToken firebaseToken;


    public void addTotalPayAmount(long payAmount) {
        this.userTotalPayAmount += payAmount;
    }

    public void addTotalReceiveAmount(long receiveAmount) {
        this.userTotalReceiveAmount += receiveAmount;
    }
}

