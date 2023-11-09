package lk.ijse.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmployeeDto {
    private String empId;
    private String name;
    private String address;
    private String position;
    private String contact;
    private String salary;
    private String userId;
}
