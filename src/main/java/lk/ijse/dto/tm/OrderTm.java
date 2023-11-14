package lk.ijse.dto.tm;

import com.jfoenix.controls.JFXButton;
import lombok.*;

import java.awt.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class OrderTm {
    private String orderId;
    private String itemName;
    private String qty;
    private String price;
    private String unitPrice;
    private String date;
    private JFXButton btnRemove;

    {
        btnRemove = new JFXButton("Remove");
        btnRemove.setCursor(javafx.scene.Cursor.HAND);
        btnRemove.setStyle("-fx-background-color: #403D39; -fx-text-fill: #ffffff");

        btnRemove.setPrefWidth(100);
        btnRemove.setPrefHeight(20);
    }

    public OrderTm(String orderId, String itemName, String qty, String price,String unitPrice, String date) {
        this.orderId = orderId;
        this.itemName = itemName;
        this.qty = qty;
        this.price = price;
        this.unitPrice = unitPrice;
        this.date = date;
    }
}
