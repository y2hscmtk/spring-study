package japshop.section5_6;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * ----------------                --------------
 * | MEMBER_ID(PK)|                | TEAM_ID(PK)|
 * |--------------|  m --------- 1 |------------|
 * | TEAM_ID(FK)  |                | NAME       |
 * | USERNAME     |                |------------|
 * |______________|
 */
@Entity
public class Team extends BaseEntity{
    @Id
    @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long teamId;
    private String name;

    // 양방향 연관관계를 위해
    @OneToMany(mappedBy = "team") // Member의 team과 연관관계가 매핑되어 있다는 의미
    private List<Member> members = new ArrayList<>(); // 연관관계에 종속되는 역할 -> 읽기 전용

    // Team 기준 연관관계 편의 메소드
    // 연관관계 편의 메소드는 한쪽에만 작성하는 것이 좋다.
//    public void addMember(Member member) {
//        member.setTeam(this);
//        members.add(member);
//    }


    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }
}
