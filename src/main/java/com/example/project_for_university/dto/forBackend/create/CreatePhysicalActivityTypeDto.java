package com.example.project_for_university.dto.forBackend.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePhysicalActivityTypeDto {
    private String name;
    private String description;
}
