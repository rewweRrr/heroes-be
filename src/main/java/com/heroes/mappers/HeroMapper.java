package com.heroes.mappers;

import com.heroes.entities.HeroEntity;
import com.heroes.models.CreateHeroDto;
import com.heroes.models.HeroDto;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class HeroMapper {

    public HeroDto map(CreateHeroDto createHeroDto) {
        return HeroDto.builder()
                .id(UUID.randomUUID())
                .name(createHeroDto.getName())
                .description(createHeroDto.getDescription())
                .build();
    }

    public HeroDto from(HeroEntity heroEntity) {
        return HeroDto.builder()
                .id(heroEntity.getId())
                .name(heroEntity.getName())
                .description(heroEntity.getDescription())
                .build();
    }

    public List<HeroDto> from(List<HeroEntity> heroEntities) {
        return heroEntities.stream().map(this::from).toList();
    }
}
