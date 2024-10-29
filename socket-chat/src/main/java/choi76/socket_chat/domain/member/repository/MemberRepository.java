package choi76.socket_chat.domain.member.repository;

import choi76.socket_chat.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {
}
