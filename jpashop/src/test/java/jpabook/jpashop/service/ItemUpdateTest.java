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
		book.setName("책이름");
		
		//변경감지 == dirty checking
		
		//TX commit
	}
	
	@Transactional
	void update(Item itemParam) { //itemParam: 파리미터로 넘어온 준영속 상태의 엔티티
		Item findItem = em.find(Item.class, itemParam.getId()); //같은 엔티티를 조회한	다.
		findItem.setPrice(itemParam.getPrice()); //데이터를 수정한다.
	}
	
}
