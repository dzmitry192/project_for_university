package com.example.project_for_university.dto.forBackend.update;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserDto {
    private String fio;
    private String email;
}
