package spring.springcorebasic;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
    excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
) // 자동으로 스프링 빈을 등록(@Component를 모두 스프링 빈으로 등록함)
// 이전에 작성했던 AppConfig가 등록되지 않도록 예외처리(Configuration 어노테이션이 붙은 클래스 제외)
public class AutoAppConfig {
    // 컴포넌트 스캔을 사용하려면 @ComponentScan을 설정 정보에 붙여주면 된다.
    // 기존의 AppConfig와 다르게 @Bean으로 등록한 빈이 하나도 없다.

    // 참고 : Configuration 이 컴포넌트 스캔의 대상이 된 이유도 @Configuration에 @Component가 붙어있기 때문
    // 이제 각 클래스가 컴포넌트 스캔의 대상이 되도록 @Component를 붙여야한다.(MemoryMemberRepository, 등)

}
