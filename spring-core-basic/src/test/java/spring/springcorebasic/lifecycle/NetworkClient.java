package spring.springcorebasic.lifecycle;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class NetworkClient {

    private String url;
    public NetworkClient() {
        System.out.println("생성자 호출, url = " + url);
//        connect();
//        call("초기화 연결 메시지");
    }

    public void setUrl(String url) {
        this.url = url;
    }

    // 서비스 시작시 호출
    public void connect() {
        System.out.println("connect = " + url);
    }

    public void call(String message) {
        System.out.println("call : " + url + " message = " + message);
    }

    // 서비스 종료시 호출
    public void disconnect() {
        System.out.println("close = " + url);
    }

    // InitializaingBean implementation
    // 의존 관계 주입이 끝나고 호출
    @PostConstruct // => 초기화 함수로 지정 => 스프링에서 권장하는 방법(스프링이 아닌 다른 컨테이너에서도 잘 동작함) 외부 라이브러리에서 적용 못한다는 단점
    public void init() throws Exception {
        System.out.println("NetworkClient.init");
        connect();
        call("초기화 연결 메시지");
    }

    // 빈이 종료될 때 호출됨
    @PreDestroy // => 소멸자로 지정
    public void close() throws Exception {
        System.out.println("NetworkClient.close");
        disconnect();
    }
}
