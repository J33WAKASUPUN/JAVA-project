package lk.ijse.dto.tm;

import lombok.*;

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
    private String date;
}
