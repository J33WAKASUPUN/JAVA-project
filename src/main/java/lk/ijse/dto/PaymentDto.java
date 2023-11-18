package lk.ijse.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PaymentDto {
    private String pId;
    private double amount;
    private String status;
    private LocalDate date;
    private String oId;
}
