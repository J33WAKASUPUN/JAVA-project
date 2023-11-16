package lk.ijse.dto;

import lombok.*;

import java.awt.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDto {
    private String userId;
    private String userName;
    private String password;
    private String email;
}
