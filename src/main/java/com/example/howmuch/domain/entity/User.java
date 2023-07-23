package com.example.howmuch.domain.entity;

import com.example.howmuch.constant.RoleType;
import com.example.howmuch.domain.BaseTimeEntity;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;

@Slf4j
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Where(clause = "USR_DELETED_AT is null")
@SQLDelete(sql = "UPDATE USERS SET USR_DELETED_AT = CURRENT_TIMESTAMP WHERE USERS.USR_ID = ?")
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

    @Column(name = "usr_phone")
    private String phoneNumber;

    @Column(name = "usr_total_pay")
    private long userTotalPayAmount;

    @Column(name = "usr_total_rcv")
    private long userTotalReceiveAmount;

    @Column(name = "usr_deleted_at")
    private LocalDateTime deletedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "usr_role", nullable = false)
    private RoleType roleType;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user")
    private FirebaseToken firebaseToken;


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

