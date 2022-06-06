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
@Transactional //test�� rollback ����
public class MemberServiceTest {
	
	@Autowired MemberService memberService;
	@Autowired MemberRepository memberRepository;
	@Autowired EntityManager em;
	
	@Test
	public void ȸ������() throws Exception {
		//Given
		Member member = new Member();
		member.setName("kim");
		
		//When
		Long saveId = memberService.join(member);
		
		/*
		 * Rollback(false)������ Test�� �ڵ� rollback ��. 
		 * Transaction�� commit�� �ȵǱ� ������ 
		 * join method�� �ִ� em.persist�� flush���� �ʾ� insert���� ���� �ʴ´�.
		 */
		
		//Then
		em.flush(); 
		/*
		 * Rollback ������̼� ��� entityMangaer�� Test Class���� ����ؼ� ������ flush�� �ϸ�
		 * insert���� ������
		 * ��� Transactional rollback�� true���� ���� data�� ���� �ʴ´�. 
		 */
		assertEquals(member, memberRepository.findOne(saveId));
	}
	
	 @Test(expected = IllegalStateException.class)
	 public void �ߺ�_ȸ��_����() throws Exception {
		//Given
		 Member member1 = new Member();
		 member1.setName("kim");
		 Member member2 = new Member();
		 member2.setName("kim");
		 
		 //When
		 memberService.join(member1);
		 memberService.join(member2); //���ܰ� �߻��ؾ� �Ѵ�.
		 
		//Then
		 fail("���ܰ� �߻��ؾ� �Ѵ�.");
	 }
	
}
