package jpabook.jpashop.service;

import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    /**
     * �ֹ�
     */
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {

        //��ƼƼ ��ȸ
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        //������� ����
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());
        delivery.setStatus(DeliveryStatus.READY);

        //�ֹ���ǰ ����
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        //�ֹ� ����
        Order order = Order.createOrder(member, delivery, orderItem);

        //�ֹ� ����
        orderRepository.save(order);

        return order.getId();
    }

    /**
     * �ֹ� ���
     */
    @Transactional
    public void cancelOrder(Long orderId) {
        //�ֹ� ��ƼƼ ��ȸ
        Order order = orderRepository.findOne(orderId);
        //�ֹ� ���
        order.cancel();
    }

    //�˻�
    public List<Order> findOrders(OrderSearch orderSearch) {
        return orderRepository.findAllByString(orderSearch);
    }
}