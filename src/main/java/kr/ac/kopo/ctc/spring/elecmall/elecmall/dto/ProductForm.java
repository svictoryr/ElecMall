package kr.ac.kopo.ctc.spring.elecmall.elecmall.dto;

import kr.ac.kopo.ctc.spring.elecmall.elecmall.entity.Product;
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
    private String productType;
    private int price;
    private int stock;
    private String imageUrl;

    public Product toEntity() {
        Product product = new Product();
        product.setProductId(this.productId);
        product.setProductName(this.productName);
        product.setProductContent(this.productContent);
        product.setProductType(this.productType);
        product.setPrice(this.price);
        product.setStock(this.stock);
        product.setImageUrl(this.imageUrl);

        return product;
    }

}
