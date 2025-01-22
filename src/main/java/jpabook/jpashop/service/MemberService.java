package jpabook.jpashop.service;


import org.springframework.transaction.annotation.Transactional;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    //@Autowired
    //MemberRepository memberRepository;

    //회원가입

    @Transactional
    public Long join(Member member) {
         validateDuplicateMember(member);
         memberRepository.save(member);
         return member.getId();
     }

     private void validateDuplicateMember(Member member) {
         List<Member> findMembers =
                 memberRepository.findByName(member.getName());
         if (!findMembers.isEmpty()) {
             throw new IllegalStateException("Duplicate member found: " + member.getName());
         }
     }

     //전체 회원 조회

    public List<Member> findMembers() {
         return memberRepository.findAll();
    }

    public Member findOne(Long id) {
         return memberRepository.findOne(id);
    }

    //회원 삭제
    public void deleteId(Long id) {
        memberRepository.delete(id);
    }
}
