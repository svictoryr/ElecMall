package kr.ac.kopo.ctc.spring.elecmall.elecmall.dto;

import kr.ac.kopo.ctc.spring.elecmall.elecmall.entity.Shop;
import kr.ac.kopo.ctc.spring.elecmall.elecmall.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter @Setter @ToString
public class ShopForm {
    private Long shopId;
    private String shopName;
    private String shopAddress;
    private String openTime;
    private String closeTime;
    private String shopTelnum;

    public Shop toEntity() {
        Shop shop = new Shop();
        shop.setShopId(this.shopId);
        shop.setShopName(this.shopName);
        shop.setShopAddress(this.shopAddress);
        shop.setOpenTime(this.openTime);
        shop.setCloseTime(this.closeTime);
        shop.setShopTelnum(this.shopTelnum);

        return shop;
    }
}
