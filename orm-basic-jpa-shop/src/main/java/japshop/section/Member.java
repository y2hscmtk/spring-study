package japshop.section;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * ----------------                --------------
 * | MEMBER_ID(PK)|                | TEAM_ID(PK)|
 * |--------------|  m --------- 1 |------------|
 * | TEAM_ID(FK)  |                | NAME       |
 * | USERNAME     |                |------------|
 * |______________|
 */
@Entity
public class Member extends BaseEntity{
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
    // FetchType => Member를 조회하는 시점에서 Team까지 조회하는것은 낭비임 -> LAZY를 사용해서 지연 로딩할 수 있음(프록시 객체로 조회)
    // Member와 해당 Member의 Team을 함께 사용하는 경우가 많다면 한번에 가져오는것이 효율적 -> EAGER를 사용해서 즉시 로딩
    @ManyToOne(fetch = FetchType.LAZY) // Member입장에서는 Many, Team 입장에서는 One
    @JoinColumn(name = "TEAM_ID") // FK가 TEAM_ID임을 명시
    private Team team; // 연관관계의 주인(다대 일에서 '다'를 연관관계의 주인으로 설정해야 편하다.)

    @OneToOne
    @JoinColumn(name="LOCKER_ID")
    private Locker locker;

//    @ManyToMany
//    @JoinTable(name = "MEMBER_PRODUCT") // 중간에 생성할 연결 테이블의 이름 설정
//    private List<Product> products = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<MemberProduct> memberProducts = new ArrayList<>();

    // 공통 속성 Period로 묶는다. (임제디드 타입(복합타입)) => 응집도를 높일 수 있음
    @Embedded // 임베디드 타입임을 명시
    private Period period; // 기본 값을 합친 임베디드 타입을 정의해서 사용 => 재사용성을 높인다.(테이블 형태는 유지됨)
    // private LocalDateTime startDate;
    // private LocalDateTime endDate;

    @Embedded
    private Address homeAddress; // 임베디드 타입

    /*
    * private String citiy;
    * private String zipcode;
    * private String street;
    * */

    // 값 타입 컬렉션
    @ElementCollection // 값 타입 컬렉션
    @CollectionTable(name = "FAFORITE_FOOD", joinColumns =
        @JoinColumn(name = "MEMBER_ID") // FAVORITE_FOOD 테이블의 FK인 MEMBER_ID와 join
    ) // 매핑될 테이블 이름
    @Column(name = "FOOD_NAME") // Favroite_food 테이블의 컬럼은 한개뿐이므로 별칭 지정
    private Set<String> favoriteFoods = new HashSet<>(); // 일대다

//    @ElementCollection // 값 타입 컬렉션
//    @CollectionTable(name = "ADDRESS", joinColumns =
//        @JoinColumn(name = "MEMBER_ID") // ADDRESS 테이블의 FK인 MEMBER_ID와 매핑
//    ) // 매핑 정보
//    private List<Address> addressesHistory = new ArrayList<>(); // 일대 다

    // 값 타입이 아닌 엔티티로 매핑하는 방법 => 특정이 가능해진다.
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="MEMBER_ID")
    private List<AddressEntity> addressesHistory = new ArrayList<>();

    public Set<String> getFavoriteFoods() {
        return favoriteFoods;
    }

    public void setFavoriteFoods(Set<String> favoriteFoods) {
        this.favoriteFoods = favoriteFoods;
    }


    public List<AddressEntity> getAddressesHistory() {
        return addressesHistory;
    }

    public void setAddressesHistory(List<AddressEntity> addressesHistory) {
        this.addressesHistory = addressesHistory;
    }

    public Locker getLocker() {
        return locker;
    }

    public void setLocker(Locker locker) {
        this.locker = locker;
    }

    public List<MemberProduct> getMemberProducts() {
        return memberProducts;
    }

    public void setMemberProducts(List<MemberProduct> memberProducts) {
        this.memberProducts = memberProducts;
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    public Address getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(Address homeAddress) {
        this.homeAddress = homeAddress;
    }

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
        team.getMembers().add(this); // 연관관계 편의 메소드 -> 실수를 방지할 수 있다.
    }
}