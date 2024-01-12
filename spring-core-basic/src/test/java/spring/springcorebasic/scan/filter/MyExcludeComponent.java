package spring.springcorebasic.scan.filter;

import java.lang.annotation.*;

// 애노테이션 만들어보기 예제 => 이 어노테이션이 붙으면 컴포넌트 스캔에서 제외함
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyExcludeComponent {


}
