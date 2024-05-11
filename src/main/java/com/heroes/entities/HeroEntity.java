package com.heroes.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.UUID;


@Data
@Entity
public class HeroEntity {
    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private String description;
}
