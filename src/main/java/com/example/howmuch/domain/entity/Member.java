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
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "mbr_nickname")
    private String nickname;

    @Column(name = "mbr_profile")
    private String profileImage;
}
