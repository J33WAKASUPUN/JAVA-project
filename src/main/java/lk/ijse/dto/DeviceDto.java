package lk.ijse.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DeviceDto {
    private String deviceId;
    private String dName;
    private String problem;
    private String status;
    private String cost;
    private String date;
    private String cId;
}
