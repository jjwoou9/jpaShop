package jpabook.jpashop.service;


import javax.persistence.EntityManager;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional 
public class ItemUpdateTest {

	@Autowired EntityManager em;
	
	@Transactional
	public void updateTest() throws Exception {
		Book book = em.find(Book.class, 1L);
		
		//TX
		book.setName("å�̸�");
		
		//���氨�� == dirty checking
		
		//TX commit
	}
	
	@Transactional
	void update(Item itemParam) { //itemParam: �ĸ����ͷ� �Ѿ�� �ؿ��� ������ ��ƼƼ
		Item findItem = em.find(Item.class, itemParam.getId()); //���� ��ƼƼ�� ��ȸ��	��.
		findItem.setPrice(itemParam.getPrice()); //�����͸� �����Ѵ�.
	}
	
}
