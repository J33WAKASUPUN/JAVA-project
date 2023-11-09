package lk.ijse.dto.tm;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StockTm {
    private String itemId;
    private String itemName;
    private String qtyOnHand;
    private String cost;
    private String unitPrice;
}
