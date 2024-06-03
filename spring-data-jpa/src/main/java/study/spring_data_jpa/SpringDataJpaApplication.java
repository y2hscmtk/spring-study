package study.spring_data_jpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;
import java.util.UUID;

@EnableJpaAuditing // UpdatedAt, CreatedAt 활용
@SpringBootApplication
public class SpringDataJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringDataJpaApplication.class, args);
	}

	@Bean
	public AuditorAware<String> auditorProvider() {
		// AuditorAware를 구현하여 반환한다.
//		return new AuditorAware<String>() {
//			@Override
//			public Optional<String> getCurrentAuditor() {
//				return Optional.of(UUID.randomUUID().toString());
//			}
//		};

		// 인터페이스에서 메소드가 한개면 람다식으로 변환 가능
		// 현재는 UUID를 설정하였으나, 실제 서비스에서는 사용자 아이디 등으로 적절히 설정해야한다.
		// 엔티티가 생성되거나 수정될 때마다 AuditorAware를 호출한다.
		return () -> Optional.of(UUID.randomUUID().toString());
	}
}
