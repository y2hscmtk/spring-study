package spring.springcorebasic.member;

// 회원 도메인
/**
 * - 회원을 가입하고 조회할 수 있다. => MemberService 로직
 * - 회원은 일반과 VIP 두 가지 등급이 있다. => Grade
 * - 회원 데이터는 자체 DB를 구축할 수 있고, 외부 시스템과 연동할 수 있다.(미확정) => MemberRepository
 * */
public class Member {
    private Long id; // 회원 아이디
    private String name; // 회원 이름
    private Grade grade; // 회원 등급

    public Member(Long id, String name, Grade grade) {
        this.id = id;
        this.name = name;
        this.grade = grade;
    }

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

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }
}
