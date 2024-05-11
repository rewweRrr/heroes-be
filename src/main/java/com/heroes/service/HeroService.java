package com.heroes.service;

import com.heroes.models.CreateHeroDto;
import com.heroes.models.HeroDto;

import java.util.List;
import java.util.UUID;

public interface HeroService {
    HeroDto createHero(CreateHeroDto hero);
    List<HeroDto> getHeroes();
    HeroDto getHero(UUID id);
}
