package kr.ac.kopo.ctc.spring.elecmall.elecmall.dto;

import kr.ac.kopo.ctc.spring.elecmall.elecmall.entity.OrderInfo;
import kr.ac.kopo.ctc.spring.elecmall.elecmall.entity.Product;
import kr.ac.kopo.ctc.spring.elecmall.elecmall.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter @Setter @ToString
public class OrderInfoForm {
    private Long orderId;
    private String orderDate;
    private String orderType;
    private int quantity;
    private int totalPrice;
    private Long userId;
    private Long ProductId;


    public OrderInfo toEntity(User user, Product product) {
        String defaultOrderType = "배송중";
        
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderId(this.orderId);
        orderInfo.setOrderDate(this.orderDate);
        orderInfo.setOrderType(defaultOrderType);
        orderInfo.setQuantity(this.quantity);
        orderInfo.setTotalPrice(this.totalPrice);
        orderInfo.setUser(user);
        orderInfo.setProduct(product);

        return orderInfo;
    }
}
