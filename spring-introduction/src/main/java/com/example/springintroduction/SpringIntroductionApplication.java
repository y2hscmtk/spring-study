package com.example.springintroduction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringIntroductionApplication {

	public static void main(String[] args) {
		// SpringApplication 의 run 을 통해 톰캣서벌르 사용하여 서버 실행
		SpringApplication.run(SpringIntroductionApplication.class, args);
	}

}
