package study.spring_data_jpa.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id","name"})
@Table(name = "TEAM")
public class Team {
    @Id
    @GeneratedValue
    @Column(name = "team_id")
    private Long id;
    private String name;

    @OneToMany(mappedBy = "team") // Member의 외래키 도메인 team과 매핑
    private List<Member> members = new ArrayList<>();

    public Team(String name) {
        this.name = name;
    }
}
