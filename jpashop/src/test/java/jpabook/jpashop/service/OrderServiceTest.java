package jpabook.jpashop.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional()
public class OrderServiceTest {

	@PersistenceContext	EntityManager em;
	
	@Autowired OrderService orderService;
	@Autowired OrderRepository orderRepository;
	
	@Test
	public void ��ǰ�ֹ�() throws Exception {
		//Given
		Member member = createMember();
		Item item = createBook("�ð� JPA", 10000, 10); //�̸�, ����, ���
		em.persist(member);
		
		int orderCount = 2;
		//When
		Long orderId = orderService.order(member.getId(), item.getId(), orderCount);
		//Then
		Order getOrder = orderRepository.findOne(orderId);
		
		assertEquals("��ǰ �ֹ��� ���´� ORDER",OrderStatus.ORDER, getOrder.getStatus());
		assertEquals("�ֹ��� ��ǰ ���� ���� ��Ȯ�ؾ� �Ѵ�.",1,getOrder.getOrderItems().size());
		assertEquals("�ֹ� ������ ���� * �����̴�.", 10000 * 2, getOrder.getTotalPrice());
		assertEquals("�ֹ� ������ŭ ��� �پ�� �Ѵ�.",8, item.getStockQuantity());
	}
	
	@Test(expected = NotEnoughStockException.class)
	 public void ��ǰ�ֹ�_�������ʰ�() throws Exception {
		Member member = createMember();
		Item item = createBook("�ð� JPA", 10000, 10); 
		int orderCount =11;
		orderService.order(member.getId(), item.getId(), orderCount);
		
		fail("��� ���� ���� ���� �߻�");
		
		
	 }
	 @Test
	 public void �ֹ����() {
		 //given
		 Member member = createMember();
		 Book item = createBook("�ð� JPA", 10000, 10);
		 int orderCount = 2;
		 Long orderId = orderService.order(member.getId(), item.getId(), orderCount);
		 
		 //when
		 orderService.cancelOrder(orderId);
		 
		 //then
		 Order getOrder = orderRepository.findOne(orderId);
		 assertEquals("�ֹ� ��ҽ� ���´� Cancel .",OrderStatus.CANCEL, getOrder.getStatus());
		 assertEquals("�ֹ� ������ŭ ��� �����ؾ� �Ѵ�.",10, item.getStockQuantity());
		 
	 }

	private Book createBook(String name, int price, int stockQuantity) {
		Book book = new Book();
		book.setName(name);
		book.setStockQuantity(stockQuantity);
		book.setPrice(price);
		em.persist(book);
		return book;
	}

	private Member createMember() {
		Member member = new Member();
		member.setName("ȸ��1");
		member.setAddress(new Address("����", "����", "123-123"));
		em.persist(member);
		return member;
	}
	
}
