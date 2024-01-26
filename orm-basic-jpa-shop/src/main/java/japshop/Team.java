package japshop;

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
public class Team {
    @Id
    @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long teamId;
    private String name;

    // 양방향 연관관계를 위해
    @OneToMany(mappedBy = "team") // Member의 team과 연관관계가 매핑되어 있다는 의미
    private List<Member> members = new ArrayList<>();

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
