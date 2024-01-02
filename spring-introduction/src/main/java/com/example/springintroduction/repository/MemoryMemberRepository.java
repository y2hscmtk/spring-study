package com.example.springintroduction.repository;

import com.example.springintroduction.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
// Option + Enter => implements all methods
public class MemoryMemberRepository implements MemberRepository {

    // save 함수를 통해 데이터를 저장하기 위한 임시 저장소
    private static Map<Long, Member> store = new HashMap<>();
    private static Long sequence = 0L;

    // 회원가입 로직 => sequnce값으로 아이디 설정
    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member); // map에 삽입
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        // store에서 id에 해당하는 사용자 찾아서 꺼내기
        // 만약 해당하는 사용자가 없으면 Null이 될수 있으므로 optional 사용
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny(); // 일치하는 값 하나라도 찾으면 반환
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values()); // 모든 사용자 반환
    }

    // 테스트에서 사용하기 위한 저장소 비우기 함수
    public void clearStore() {
        store.clear();
    }
}
