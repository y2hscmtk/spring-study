package spring.springcorebasic;

// Lombok : Getter & Setter 자동 생성 라이브러리
// IntellJ - Settings - Plugin - Lombok 검색
// IntellJ - Settings - Annotaion Processser - Enable ~~ 체크하기 => 롬복 사용을 위해

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class HelloLombok {
    private String name;
    private int age;

    public static void main(String[] args) {
        HelloLombok helloLombok = new HelloLombok();
        helloLombok.setName("hello");

        String name = helloLombok.getName();
        System.out.println("name = " + name);
    }
}
