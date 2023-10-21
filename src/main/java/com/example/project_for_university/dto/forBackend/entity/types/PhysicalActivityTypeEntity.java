package com.example.project_for_university.dto.forBackend.entity.types;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhysicalActivityTypeEntity {
    private int id;
    private String name;
    private String description;
}
