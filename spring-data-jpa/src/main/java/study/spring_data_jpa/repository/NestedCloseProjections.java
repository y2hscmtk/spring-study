package study.spring_data_jpa.repository;

// 회원 객체와 연관된 팀 객체까지 조회가 가능할까?
public interface NestedCloseProjections {
    String getUsername(); // select username
    TeamInfo getTeam(); // select team

    // => SELECT username,team FROM Member WHERE name = 'm1';

    // 연관된 팀
    interface TeamInfo {
        String getName();
    }
}
