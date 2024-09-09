package kr.ac.kopo.ctc.spring.elecmall.elecmall.dto;

import kr.ac.kopo.ctc.spring.elecmall.elecmall.entity.OrderInfo;
import kr.ac.kopo.ctc.spring.elecmall.elecmall.entity.Product;
import kr.ac.kopo.ctc.spring.elecmall.elecmall.entity.Sale;
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
    private int Quantity;
    private int totalPrice;


    public OrderInfo toEntity(Product product) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderId(this.orderId);
        orderInfo.setOrderDate(this.orderDate);
        orderInfo.setOrderType(this.orderType);
        orderInfo.setQuantity(this.Quantity);
        orderInfo.setTotalPrice(this.totalPrice);
        orderInfo.setProduct(product);

        return orderInfo;
    }
}
