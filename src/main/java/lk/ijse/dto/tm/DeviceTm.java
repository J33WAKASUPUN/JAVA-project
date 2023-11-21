package lk.ijse.dto.tm;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DeviceTm {
    private String deviceId;
    private String dName;
    private String problem;
    private String status;
    private String cost;
    private LocalDate date;
}
