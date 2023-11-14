package lk.ijse.dto.tm;

import com.jfoenix.controls.JFXButton;
import lombok.*;

import java.awt.*;
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
    private JFXButton btn;

    public OrderTm(String itemId, String itemName, int qty, double price,double unitPrice, JFXButton btn) {
        this.ItemId = itemId;
        this.itemName = itemName;
        this.qty = qty;
        this.price = price;
        this.unitPrice = unitPrice;
        this.btn = btn;
    }
}
