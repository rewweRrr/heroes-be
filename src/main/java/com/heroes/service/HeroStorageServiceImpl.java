package com.heroes.service;

import com.heroes.models.HeroDto;
import jakarta.inject.Singleton;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Singleton
public class HeroStorageServiceImpl implements HeroStorageService{

    private final List<HeroDto> heroes = new ArrayList<>();

    public HeroStorageServiceImpl() {
        heroes.add(HeroDto.builder().id(UUID.randomUUID()).name("Hero 1").build());
    }


    @Override
    public List<HeroDto> getHeroes() {
        return heroes;
    }

    @Override
    public HeroDto addHero(HeroDto hero) {
        heroes.add(hero);
        return hero;
    }

    @Override
    public HeroDto getHero(UUID id) {
        return heroes.stream().filter(hero -> hero.getId().equals(id)).findFirst().orElse(null);
    }
}
