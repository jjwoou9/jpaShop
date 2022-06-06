package jpabook.jpashop.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor //������ ������ + final�ִ� �͸� 
public class MemberService {

	private final MemberRepository memberRepository;
	
	@Transactional //����
	public Long join(Member member) {
		validateDuplicateMember(member); //�ߺ� ȸ�� ����
		memberRepository.save(member);
		return member.getId();
	}
	
	private void validateDuplicateMember(Member member) {
		List<Member> findMembers = memberRepository.findByName(member.getName());
		if(!findMembers.isEmpty()) {
			throw new IllegalStateException("�̹� �����ϴ� ȸ���Դϴ�.");
		}
	}
	
	//ȸ�� ��ü ��ȸ
	public List<Member> findMembers() {
		return memberRepository.findAll();
	}
	
	public Member findOne(Long memberId) {
		return memberRepository.findOne(memberId);
	}
}
