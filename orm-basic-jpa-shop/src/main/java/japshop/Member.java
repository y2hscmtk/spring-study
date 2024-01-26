package japshop;

import jakarta.persistence.*;

/**
 * ----------------                --------------
 * | MEMBER_ID(PK)|                | TEAM_ID(PK)|
 * |--------------|  m --------- 1 |------------|
 * | TEAM_ID(FK)  |                | NAME       |
 * | USERNAME     |                |------------|
 * |______________|
 */
@Entity
public class Member {
    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id; // PK
    @Column(name = "USERNAME")
    private String username;

//    테이블 관점에서의 연관관계 설정 => FK를 기준으로 조인
//    => 자바는 참조를 기준으로 하기 때문에 참조 방식으로 매핑하기 힘듬
//    @Column(name = "TEAM_ID")
//    private Long teamId; // FK

    // 객체지향관점에서의 사용(참조를 이용) => JPA에게 어떤 관계인지 알려야함
    @ManyToOne // Member입장에서는 Many, Team 입장에서는 One
    @JoinColumn(name = "TEAM_ID") // FK가 TEAM_ID임을 명시
    private Team team;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}