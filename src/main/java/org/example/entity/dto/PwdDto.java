package org.example.entity.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PwdDto {

    private String id;
    private String oldpwd;
    private String newpwd;
}
