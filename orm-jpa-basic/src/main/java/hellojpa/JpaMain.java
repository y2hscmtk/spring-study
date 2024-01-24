package hellojpa;

import jakarta.persistence.*;

public class JpaMain {
    // [JPA 구동 방식]
    // 1. Persistence - 설정 정보 조회 -> META-INF/persistence.xml (persistence.xml을 바탕으로 설정정보 조회)
    // 2. Persistence - 생성 -> EntityManagerFactory(어플리케이션 로딩 시점에 하나만 생성되어야함) -> 생성 -> EntityManager(하나의 작업마다)
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello"); // hello라는 이름으로 생성
    static EntityManager em = emf.createEntityManager(); // EntityManger => 자바 컬렉션처럼 이해 => 내 객체를 대신 저장해주는 존재

    public static void main(String[] args) {

        // INSERT MEMBER 회원 가입
        insertMember();

        // SELECT MEMBER 회원 조회
        findMember(2L);

        // UPDATE MEMBER 회원 정보 수정
        updateMember(2L);


        /**
         * 엔티티 매니저와 영속성 컨텍스트
         * - 엔티티 매니저는 엔티티 매니저 펙토리에 의해 요청이 들어올때마다 새롭게 생성된다.
         * - 영속성 컨텍스트는 엔티티 매니저에 속한 것으로, 엔티티를 영구 저장하는 환경을 말한다.
         * */

        // 1. 비영속 상태 => 엔티티 매니저와 아무런 관련이 없는 상태, DB에 들어가지도 않는다.
        Member member = new Member();
        member.setName("HelloJPA");
        member.setId(100L);

        // 2. 영속 상태
        // member는 persist 명령에 의해 영속상태가 됨, DB에 저장되지는 않음(BEFORE ~~ AFTER 안에 쿼리문 존재하는지 확인)
        System.out.println("=== BEFORE ===");
        em.persist(member);
        System.out.println("=== AFTER ===");

        // 3. 준영속, 삭제
        // detach 명령을 통해 영속성 컨텍스트에서 회원 엔티티를 분리, 준영속 상태가 됨
        // em.detach(member);
        // 객체를 삭제한 상태(삭제)
        // em.remove(member);



        // 작업이 끝나면 EntityManager, EntityMangagerFactory 종료
        em.close();
        emf.close();
    }

    /**
     * INSERT MEMBER 회원 가입
     * */
    private static void insertMember(){

        // 데이터를 변경하는 모든 작업은 트렌젝션 안에서 수행 되어야 함
        EntityTransaction tx = em.getTransaction(); // 트렌젝션 생성
        tx.begin(); // 트랜젝션 시작

        Member member = new Member(); // 회원 하나 생성
        member.setId(2L);
        member.setName("helloA");
        em.persist(member); // Member테이블에 생성한 회원 객체 매핑하여 Insert

        tx.commit(); // 작업 완료 후 상태 반영(commit)
    }

    /**
     * SELECT MEMBER 회원 조회
     */
    private static void findMember(Long id) {
        EntityTransaction tx = em.getTransaction(); // 트렌젝션 생성
        tx.begin(); // 트랜젝션 시작

        // EntityManger로부터 찾을 객체 명시
        Member findMember = em.find(Member.class, id); // id : 찾으려는 객체의 PK
        System.out.println("findMember = " + findMember.toString()); // 결과물 확인
        tx.commit(); // 작업 완료 후 상태 반영(commit)
    }

    /**
     * UPDATE MEMBER 회원 정보 업데이트
     * */
    private static void updateMember(Long id){
        EntityTransaction tx = em.getTransaction(); // 트렌젝션 생성
        tx.begin(); // 트랜젝션 시작

        // EntityManger로부터 찾을 객체 명시, 찾으려는 객체의 PK 명싱
        Member findMember = em.find(Member.class, id);

        // 이름 변경
        findMember.setName("changedName"); // 변경만하고 commit하지 않아도 변경된 상태가 유지된다.
        // => 마치 자바의 컬렉션을 다루듯 데이터베이스를 이용할 수 있도록 설계되었기 때문

        System.out.println("findMember = " + findMember.toString()); // 결과물 확인
        tx.commit(); // 작업 완료 후 상태 반영(commit)

    }

}
