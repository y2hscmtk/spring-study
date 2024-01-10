package spring.springcorebasic.beandefinition;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.springcorebasic.AppConfig;

public class BeanDefinitionTest {
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    // BeanDefinition -> 설정정보를 통해 불러온 인스턴스의 메타 데이터에 해당
    // 이 메타데이터를 바탕으로 인스턴스를 생성하여 스프링 빈으로 컨테이너에 등록한다.
    // BeanDefinition의 도움으로 인해 설정 정보의 형식에 관계없이 다양한 형태의 설정정보를 사용할 수 있다.
    @Test
    @DisplayName("빈 설정 메타정보 확인")
    void findApplicationBean() {
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitionName);

            if (beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION) {
                System.out.println("beanDefinition = " + beanDefinition +
                        " beanDefinition = " + beanDefinition);
            }
        }
    }
}
