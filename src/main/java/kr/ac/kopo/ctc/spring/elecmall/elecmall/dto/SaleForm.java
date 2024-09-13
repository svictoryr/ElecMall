//package kr.ac.kopo.ctc.spring.elecmall.elecmall.dto;
//
//import kr.ac.kopo.ctc.spring.elecmall.elecmall.entity.Product;
//import kr.ac.kopo.ctc.spring.elecmall.elecmall.entity.Sale;
//import kr.ac.kopo.ctc.spring.elecmall.elecmall.entity.Shop;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.Setter;
//import lombok.ToString;
//
//@AllArgsConstructor
//@Getter @Setter @ToString
//public class SaleForm {
//    private Long saleId;
//    private String saleRate;
//    private Long productId;
//
//    public Sale toEntity(Product product) {
//        Sale sale = new Sale();
//        sale.setSaleId(this.saleId);
//        sale.setSaleRate(this.saleRate);
//        sale.setProduct(product);
//
//        return sale;
//    }
//}
