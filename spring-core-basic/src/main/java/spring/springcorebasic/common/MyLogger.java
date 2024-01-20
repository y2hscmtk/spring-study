package spring.springcorebasic.common;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.UUID;


// 웹 스코프는 요청 하나당 하나 생성되고, 요청이 끝나면 소멸된다.
// proxyMode => 프록시 클래스를 가상으로 생성
@Component
@Scope(value = "request",proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MyLogger {

    private String uuid;
    private String requestURL;

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    public void log(String meesage) {
        System.out.println("[" + uuid + "]" + "[" + requestURL + "]" + meesage);
    }

    @PostConstruct
    public void init() {
        uuid = UUID.randomUUID().toString(); // 고유한 값으로 생성
        System.out.println("[" + uuid + "] request scope bean create:" + this);
    }

    @PreDestroy
    public void close() {
        System.out.println("[" + uuid + "] request scope bean close:" + this);
    }
}
