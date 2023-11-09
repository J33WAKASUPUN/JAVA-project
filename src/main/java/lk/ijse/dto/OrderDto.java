package lk.ijse.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderDto {
    private String orderId;
    private String qty;
    private String date;
    private String price;
    private String cId;
    private String pId;
    private String deliveryId;
}
