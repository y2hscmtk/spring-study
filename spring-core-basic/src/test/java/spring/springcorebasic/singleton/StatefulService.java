package spring.springcorebasic.singleton;

public class StatefulService {

    // 싱글톤 객체이므로 price는 여러쓰레드들 사이에서 공유되는 필드이다.
    // private int price; //상태를 유지하는 필드 (stateful)
    // 공유필드를 사용하지 않아야한다.
//
//    public void order(String name, int price) {
//        System.out.println("name = " + name + " price = " + price);
//        this.price = price; // 이런식으로 작성하면 문제가 발생한다!
//    }

    public int order(String name, int price) {
        System.out.println("name = " + name + " price = " + price);
        return price; // Stateless한 설계
    }

//    public int getPrice() {
//        return price;
//    }
}
