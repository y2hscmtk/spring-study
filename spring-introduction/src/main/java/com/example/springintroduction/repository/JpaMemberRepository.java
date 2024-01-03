package com.example.springintroduction.repository;

import com.example.springintroduction.domain.Member;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository
{
    // JPA는 EntityManager를 사용하여 관리됨
    private final EntityManager em;

    // EntityManager는 스프링빈으로 생성되어 스프링 컨테이너에 존재하고
    // 생성자가 한 개이므로 AutoWired되어 DI됨
    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        em.persist(member); // 저장 => JdbcTemplate에 비해 간소화된 모습
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class,id);
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        // 객체를 대상으로 쿼리를 날림
        // Member(m) 엔티티를 조회하라
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }
}
