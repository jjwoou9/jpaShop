package jpabook.jpashop.domain;


import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Delivery {

	@Id @GeneratedValue
	@Column(name = "delivery_id")
	private Long id;
	
	@OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
	 private Order order;
	
	@Embedded
	private Address address;
	
	@Enumerated(EnumType.STRING) //enum 타입 쓸때 필수. type ordinal은 숫자로 들어감. 절대 사용 X
	private DeliveryStatus status; //ENUM [READY(준비), COMP(배송)]
}
