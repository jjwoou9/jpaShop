package jpabook.jpashop.service;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.persistence.EntityManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional //test면 rollback 제공
public class MemberServiceTest {
	
	@Autowired MemberService memberService;
	@Autowired MemberRepository memberRepository;
	@Autowired EntityManager em;
	
	@Test
	public void 회원가입() throws Exception {
		//Given
		Member member = new Member();
		member.setName("kim");
		
		//When
		Long saveId = memberService.join(member);
		
		/*
		 * Rollback(false)없으면 Test라서 자동 rollback 됨. 
		 * Transaction이 commit이 안되기 때문에 
		 * join method에 있는 em.persist가 flush되지 않아 insert문이 들어가지 않는다.
		 */
		
		//Then
		em.flush(); 
		/*
		 * Rollback 어노테이션 대신 entityMangaer를 Test Class에서 사용해서 강제로 flush를 하면
		 * insert문은 돌지만
		 * 대신 Transactional rollback은 true여서 실제 data는 들어가지 않는다. 
		 */
		assertEquals(member, memberRepository.findOne(saveId));
	}
	
	 @Test(expected = IllegalStateException.class)
	 public void 중복_회원_예외() throws Exception {
		//Given
		 Member member1 = new Member();
		 member1.setName("kim");
		 Member member2 = new Member();
		 member2.setName("kim");
		 
		 //When
		 memberService.join(member1);
		 memberService.join(member2); //예외가 발생해야 한다.
		 
		//Then
		 fail("예외가 발생해야 한다.");
	 }
	
}
