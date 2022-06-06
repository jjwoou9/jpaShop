package jpabook.jpashop.domain;

import javax.persistence.Embeddable;

import lombok.Getter;

@Embeddable
@Getter
public class Address {
	//�� Ÿ���� ���� �Ұ����ϰ� �����ؾ� �Ѵ�. => @Setter����
	private String city;
	private String street;
	private String zipcode;
	
	/*
	 * JPA ����� ��Ƽ
	 * Ƽ�� �Ӻ���� Ÿ��( @Embeddable )�� �ڹ� �⺻ ������(default constructor)�� public �Ǵ�
	 * protected �� �����ؾ� �Ѵ�. public ���� �δ� �� ���ٴ� protected �� �����ϴ� ���� �׳��� �� �����ϴ�.
	 */
	protected Address() {
		
	}
	
	public Address(String city, String street, String zipcode) {
		this.city = city;
		this.street = street;
		this.zipcode = zipcode;
	}
}
