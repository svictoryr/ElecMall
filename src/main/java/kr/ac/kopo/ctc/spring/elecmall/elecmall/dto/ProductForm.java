package kr.ac.kopo.ctc.spring.elecmall.elecmall.dto;

import kr.ac.kopo.ctc.spring.elecmall.elecmall.entity.Product;
import kr.ac.kopo.ctc.spring.elecmall.elecmall.entity.Reservation;
import kr.ac.kopo.ctc.spring.elecmall.elecmall.entity.Shop;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter @Setter @ToString
public class ProductForm {
    private Long productId;
    private String productName;
    private String productContent;
    private int price;
    private int stock;

    public Product toEntity() {
        Product product = new Product();
        product.setProductId(this.productId);
        product.setProductName(this.productName);
        product.setProductContent(this.productContent);
        product.setPrice(this.price);
        product.setStock(this.stock);

        return product;
    }
}
