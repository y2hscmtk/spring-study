package spring.springcorebasic.scan;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.springcorebasic.AutoAppConfig;
import spring.springcorebasic.member.MemberService;

public class AutoAppConfigTest {

    // @Component 와 @Autowired를 활용한 컴포넌트 스캔
    @Test
    void basicScan() {
        // 기존과 동일
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class);
        MemberService memberService = ac.getBean(MemberService.class);
        Assertions.assertThat(memberService).isInstanceOf(MemberService.class);
    }

}
