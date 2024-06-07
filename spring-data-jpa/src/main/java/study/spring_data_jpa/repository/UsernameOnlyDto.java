package study.spring_data_jpa.repository;

// 객체 타입의 프로젝션
public class UsernameOnlyDto {

    private final String username;

    // 생성자의 파라미터 이름으로 프로젝션을 매칭시킨다.

    public UsernameOnlyDto(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

}
