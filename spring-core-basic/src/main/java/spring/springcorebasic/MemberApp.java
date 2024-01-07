package spring.springcorebasic;

import spring.springcorebasic.member.Grade;
import spring.springcorebasic.member.Member;
import spring.springcorebasic.member.MemberService;
import spring.springcorebasic.member.MemberServiceImpl;

public class MemberApp {

    public static void main(String[] args) {
        MemberService memberService = new MemberServiceImpl();
        Member member = new Member(1L, "memberA", Grade.VIP);
        memberService.join(member);

        Member findMember = memberService.findMember(1L);
        System.out.println("new member = " + member.getName());
        System.out.println("find Member = " + findMember.getName());
    }
}
