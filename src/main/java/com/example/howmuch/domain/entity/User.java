package com.example.howmuch.domain.entity;

import com.example.howmuch.contant.UserStatus;
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
    private Long id;

    @Column(name = "mbr_name")
    private String name;

    @Column(name = "mbr_oauth_id")
    private String oauthId;

    @Column(name = "mbr_nickname")
    private String nickname;

    @Column(name = "mbr_profile")
    private String profileImage;

    @Column(name = "mbr_status")
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;
}
