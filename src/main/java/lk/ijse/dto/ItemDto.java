package lk.ijse.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ItemDto {
    private String itemId;
    private String itemName;
    private String qtyOnHand;
    private String cost;
    private String unitPrice;
}
