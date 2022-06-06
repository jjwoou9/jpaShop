package jpabook.jpashop.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import jpabook.jpashop.domain.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "order_item")
@Getter @Setter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class OrderItem {

	@Id @GeneratedValue
	@Column(name = "order_item_id")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_id")
	private Item item; //주문 상품
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id")
	private Order order; //주문
	
	private int orderPrice; //주문 가격
	
	private int count; //주문 수량

	//==생성 메서드==//
	public static OrderItem createOrderItem(Item item, int orderPrice, int	count) {
		OrderItem orderItem = new OrderItem();
		orderItem.setItem(item);
		orderItem.setOrderPrice(orderPrice);
		orderItem.setCount(count);
		item.removeStock(count);
		return orderItem;
	} 
	
	//==비즈니스 로직==//
	/** 주문 취소 */
	public void cancel() {
		getItem().addStock(count);
	}
	
	//==조회 로직==//
	/** 주문상품 전체 가격 조회 */
	public int getTotalPrice() {
		return getOrderPrice() * getCount();
	}
}
