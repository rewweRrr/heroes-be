package com.heroes.models;

import lombok.Data;

@Data
public class CreateHeroDto {
    private String name;
    private String description;
}
