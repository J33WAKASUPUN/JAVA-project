package lk.ijse.dto.tm;

import lombok.*;

import java.awt.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderTm {
    private String orderId;
    private String itemName;
    private String qty;
    private String price;
    private String date;
    private Button Action;
}
