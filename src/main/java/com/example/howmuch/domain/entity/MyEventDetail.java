package com.example.howmuch.domain.entity;

import com.example.howmuch.domain.BaseTimeEntity;
import com.example.howmuch.dto.event.GetAllMyEventDetailResponseDto;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@Slf4j
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "my_event_details")
public class MyEventDetail extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_detail_id")
    private Long id;

    @Column(name = "ac_name", nullable = false)
    private String acquaintanceNickname;

    @Column(name = "rcv_amnt", nullable = false)
    private Long receiveAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "my_event_id")
    private MyEvent myEvent;

    public GetAllMyEventDetailResponseDto from() {
        return GetAllMyEventDetailResponseDto.builder()
                .acquaintanceNickname(acquaintanceNickname)
                .receiveAmount(receiveAmount)
                .build();
    }
}
