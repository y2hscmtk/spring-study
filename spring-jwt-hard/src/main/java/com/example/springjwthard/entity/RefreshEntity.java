package com.example.springjwthard.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

/**
 * Redis 등의 데이터베이스를 사용하면 TTL 기능에 의해 만료시간이 지난 레코드를 자동으로 제거할 수 있다.
 */
@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "refresh")
public class RefreshEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username; // 누구의 토큰인지
    private String refresh;
    private String expiration; // 언제 만료되는지
}
