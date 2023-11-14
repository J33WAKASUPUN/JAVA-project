package lk.ijse.dto;

import lk.ijse.dto.tm.OrderTm;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderDto {
    private String orderId;
    private LocalDate date;
    private String cId;

    private List<OrderTm> orderTmList = new ArrayList<>();
}
