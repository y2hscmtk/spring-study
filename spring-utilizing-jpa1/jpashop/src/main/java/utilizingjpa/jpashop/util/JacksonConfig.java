package utilizingjpa.jpashop.util;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.jakarta.Hibernate5JakartaModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/**
 * 지연로딩된 객체 그 자체를 리턴값으로 사용 할 때, Jackson 라이브러리는 프록시 객체를 반환하려고 하여 오류가 발생한다.
 * 이 문제를 해결하는 방법은 2가지가 있다.
 * 1. 엔티티를 리턴하지 않고 DTO를 리턴한다.
 * 2. Jackson5 라이브러리를 등록하여 프록시 객체에 대해 무시하로독 구성파일을 추가한다.
 */
@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper objectMapper = builder.createXmlMapper(false).build();
        Hibernate5JakartaModule hibernateModule = new Hibernate5JakartaModule();
        //hibernateModule.configure(Hibernate5JakartaModule.Feature.FORCE_LAZY_LOADING, false); // 지연 로딩된 속성 무시
        objectMapper.registerModule(hibernateModule);
        return objectMapper;
    }
}


