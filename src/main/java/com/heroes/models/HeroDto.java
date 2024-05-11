package com.heroes.models;


import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class HeroDto {
    private UUID id;
    private String name;
    private String description;
}
