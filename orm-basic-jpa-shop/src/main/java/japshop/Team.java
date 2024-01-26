package japshop;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

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
}
