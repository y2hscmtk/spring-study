package com.example.springintroduction.domain;


import jakarta.persistence.*;

// domain : 데이터베이스에 저장되고 관리되는 모델?
@Entity
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // DB의 이름이 username인 값과 매핑
    // @Column(name = "username")
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
