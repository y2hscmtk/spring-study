package choi76.socket_chat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * reference : https://velog.io/@sunkyuj/Spring-%EC%9B%B9%EC%86%8C%EC%BC%93%EC%9C%BC%EB%A1%9C-%EC%8B%A4%EC%8B%9C%EA%B0%84-%EC%B1%84%ED%8C%85-%EA%B5%AC%ED%98%84
 */

@SpringBootApplication
@EnableJpaAuditing
public class SocketChatApplication {

	public static void main(String[] args) {
		SpringApplication.run(SocketChatApplication.class, args);
	}

}
