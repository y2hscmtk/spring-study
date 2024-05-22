package utilizingjpa.jpashop;

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;

@SpringBootTest
class JpashopApplicationTests {

	@Test
	void contextLoads() {
	}

	// 지연로딩을 할 경우, 연관관계 매핑이 되어있는 다른 객체는 Proxy객체로 생성된 상태이다.
	// 이 프록시 객체를 반환하려고 하는 경우 JackSon 라이브러리에서 오류가 발생한다.
	// 이를 해결하는 방법은 2가지가 있다.
	// 1. 엔티티를 반환하지 않고 DTO를 반환한다.
	// 2. Hibernate5JakartaModule 라이브러리를 등록하여 프록시의 경우 반환하지 않도록 무시하게 설정한다.
	@Bean
	Hibernate5Module hibernate5Module() {
		return new Hibernate5Module();
	}

}
