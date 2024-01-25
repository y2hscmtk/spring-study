package hellojpa;

import jakarta.persistence.*;

public class JpaMain {

    public static void main(String[] args) {
        // [JPA 구동 방식]
        // 1. Persistence - 설정 정보 조회 -> META-INF/persistence.xml (persistence.xml을 바탕으로 설정정보 조회)
        // 2. Persistence - 생성 -> EntityManagerFactory(어플리케이션 로딩 시점에 하나만 생성되어야함) -> 생성 -> EntityManager(하나의 작업마다)
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello"); // hello라는 이름으로 생성
        EntityManager em = emf.createEntityManager(); // EntityManger => 자바 컬렉션처럼 이해 => 내 객체를 대신 저장해주는 존재

        // INSERT MEMBER 회원 가입
        // insertMember(em,1L,"member1");

        // SELECT MEMBER 회원 조회
        // findMember(em,2L);

        // UPDATE MEMBER 회원 정보 수정
        // updateMember(em,2L);


        /**
         * 엔티티 매니저와 영속성 컨텍스트
         * - 엔티티 매니저는 엔티티 매니저 펙토리에 의해 요청이 들어올때마다 새롭게 생성된다.
         * - 영속성 컨텍스트는 엔티티 매니저에 속한 것으로, 엔티티를 영구 저장하는 환경을 말한다.
         * */

        // 데이터를 변경하는 모든 작업은 트렌젝션 안에서 수행 되어야 함
        EntityTransaction tx = em.getTransaction(); // 트렌젝션 생성
        tx.begin(); // 트랜젝션 시작
        try{
            // 1. 비영속 상태 => 엔티티 매니저와 아무런 관련이 없는 상태, DB에 들어가지도 않는다.
            Member member = new Member(104L,"HelloJPA");
//            member.setName("HelloJPA");
//            member.setId(101L);

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

            // 1차 캐시 - 조회
            Member findMember1 = em.find(Member.class, 104L); // 조회를 하였음에도 쿼리를 보면 SELECT 쿼리가 존재하지 않음
            // 저장할때 1차 캐시에 저장하였기 때문에, DB에서 조회하지 않고 바로 찾는다.

            // 종료 후 테스트 => DB에 존재하지 않으므로 SELECT문을 사용해서 조회 : findMember1 그 후 1차 캐시에 객체 저장
            // 2차 조회시(findMember2) DB에 접근하지 않고 1차 캐시에 존재하는 객체를 조회해서 가져온다.(pk로 매핑)
            Member findMember2 = em.find(Member.class, 104L);

            // 1차 캐시에 존재했던 객체를 다시 불러온 것이므로, findMember1과 findMember2는 똑같은 인스턴스이다.
            System.out.println("findMember1.toString() = " + findMember1.toString());
            System.out.println("findMember2.toString() = " + findMember2.toString());

            // 영속성 컨텍스트로 인해, 트랜잭션을 '쓰기 지연' 하여 데이터베이스에 바로 SQL을 보내지 않는다.
            // commit하는 순간에 데이터베이스에 SQL문을 보낸다.
            // 그 이전까지는 쿼리문을 생성하여 '쓰기 지연 저장소'에 쌓다가 한번에 DB로 커밋을 보낸다.

            // JPA는 컬렉션처럼 DB를 사용하는 것이 목표이기때문에, 조회 이후 값이 수정되었다고 해서 다시 persist하는것은 옳지 않다.
            // (컬렉션에서 객체의 인스턴스를 가져온 이후 값을 변경하면 컬렉션안의 해당 객체도 같은 인스턴스이므로 값이 바뀌지 않는가)
            // 이렇게 해도 되는 이유는 영속석 컨테스트의 변경 감지 기능 덕분
            // 값을 바꾸면 변경을 감지하고, 트렌젝션이 커밋되는 시점에 변경을 반영한다.
            
            // Flush
            Member flushMember = new Member(202L, "member200");
            em.persist(flushMember);
            // 강제로 커밋한것과 같은 효과를 얻는 방법 flush
            // 영속성 컨텍스트의 변경내용을 데이터베이스에 동기화 한다.
            // 트랜잭션이라는 작업 단위가 중요 -> 커밋 직전에만 동기화하면 된다.
            em.flush(); // Insert 쿼리가 이 시점에 즉시 발생하게 된다.
            System.out.println("==========================");

            // 준영속
            Member findMember = em.find(Member.class, 202L);
            findMember.setUsername("AAAA"); // 이름 변경
            em.detach(findMember); // 준 영속 상태로 변경 => 업데이트 쿼리가 나가지 않는다.(영속성컨텍스트에서 제외되었기때문에)
            // => 실제 DB에는 값이 남아있지만, 영속성에서 제외했기때문에 JPA에 의한 상태변화가 적용되지않는다.

            tx.commit(); // 작업 완료 후 상태 반영(commit)
        } catch (Exception e){
            tx.rollback();
        }
        // 작업이 끝나면 EntityManager, EntityMangagerFactory 종료
        em.close();
        emf.close();
    }

    /**
     * INSERT MEMBER 회원 가입
     * */
    private static void insertMember(EntityManager em,Long id,String name){

        // 데이터를 변경하는 모든 작업은 트렌젝션 안에서 수행 되어야 함
        EntityTransaction tx = em.getTransaction(); // 트렌젝션 생성
        tx.begin(); // 트랜젝션 시작

        Member member = new Member(id,name); // 회원 하나 생성
        em.persist(member); // Member테이블에 생성한 회원 객체 매핑하여 Insert

        tx.commit(); // 작업 완료 후 상태 반영(commit)
    }

    /**
     * SELECT MEMBER 회원 조회
     */
    private static void findMember(EntityManager em, Long id) {
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
    private static void updateMember(EntityManager em, Long id){
        EntityTransaction tx = em.getTransaction(); // 트렌젝션 생성
        tx.begin(); // 트랜젝션 시작

        // EntityManger로부터 찾을 객체 명시, 찾으려는 객체의 PK 명싱
        Member findMember = em.find(Member.class, id);

        // 이름 변경
        findMember.setUsername("changedName"); // 변경만하고 commit하지 않아도 변경된 상태가 유지된다.
        // => 마치 자바의 컬렉션을 다루듯 데이터베이스를 이용할 수 있도록 설계되었기 때문

        System.out.println("findMember = " + findMember.toString()); // 결과물 확인
        tx.commit(); // 작업 완료 후 상태 반영(commit)

    }

}
