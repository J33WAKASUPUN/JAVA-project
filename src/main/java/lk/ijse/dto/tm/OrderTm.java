package lk.ijse.dto.tm;

import lombok.*;

import javafx.scene.control.Button;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Data
public class OrderTm {
    private String ItemId;
    private String itemName;
    private int qty;
    private double price;
    private double unitPrice;
    private Button btn;

    public OrderTm(String itemId, String itemName, int qty, double price,double unitPrice, Button btn) {
        this.ItemId = itemId;
        this.itemName = itemName;
        this.qty = qty;
        this.price = price;
        this.unitPrice = unitPrice;
        this.btn = btn;
    }
}
