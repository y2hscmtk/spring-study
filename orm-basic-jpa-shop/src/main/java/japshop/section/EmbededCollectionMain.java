package japshop.section;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.Set;

// 기본 값 타입 컬렉션 연습
public class EmbededCollectionMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member member = new Member();
            member.setUsername("member1");
            // 값 하나만 생성 => 테이블에 들어감
            member.setHomeAddress(new Address("city","street","zipcode"));

            // 값 타입 컬렉션
            // 컬렉션에 값 삽입 -> 연관된 테이블 생성후 테이블에 값 삽입
            member.getFavoriteFoods().add("치킨");
            member.getFavoriteFoods().add("피자");
            member.getFavoriteFoods().add("족발");

            member.getAddressesHistory().add(new AddressEntity("city1","street1","zipcode1"));
            member.getAddressesHistory().add(new AddressEntity("city2","street2","zipcode2"));
            member.getAddressesHistory().add(new AddressEntity("city3","street3","zipcode3"));

            em.persist(member);

            em.flush();
            em.clear();

            System.out.println("===========START============");
            Member findMember = em.find(Member.class, member.getId());
            // 컬렉션은 기본적으로 지연 로딩임
            // 조회
//            Set<String> favoriteFoods = findMember.getFavoriteFoods();
//            for (String favoriteFood : favoriteFoods) {
//                System.out.println("favoriteFood = " + favoriteFood);
//            }
            // 값 타입 수정
            // homecity -> newcity => 불가(x) : 값 타입은 immutable해야되기 때문에 수정하면 안된다.
            // 사이드 이펙트(부작용)이 발생할 수 있기 때문
            // findMember.getHomeAddress().setCitiy(""); (x)
            // 인스턴스를 통으로 갈아끼우는 방식으로해야함
            Address a = findMember.getHomeAddress();
            findMember.setHomeAddress(new Address("newCitiy",a.getCitiy(),a.getZipcode()));
            // 값 타입 컬렉션 수정
            // 치킨 -> 한식
            findMember.getFavoriteFoods().remove("치킨"); // 치킨 제거
            findMember.getFavoriteFoods().add("한식"); // 한식 추가 => 수정된 효과

            // 주소 수정(값 타입 컬렉션 수정
            // 동작 과정 : equals를 사용해서 값을 찾음 -> equals가 제대로 구현되어있지 않으면 안된다.
            // 수정하고자 하는 인스턴스와 동일한 값을 동일하게 넣어줘야함
            findMember.getAddressesHistory().remove(new AddressEntity("city1","street1","zipcode1"));
            findMember.getAddressesHistory().add(new AddressEntity("newCity1","newStreet1","newZipcode1"));
            // 테이블의 데이터를 전부 갈아끼우는 방식으로 동작함(특정 요소만 delete하는 것이 아님)
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
