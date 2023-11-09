package lk.ijse.dto.tm;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmployeeTm {
    private String empId;
    private String name;
    private String address;
    private String position;
    private String contact;
    private String salary;
}
