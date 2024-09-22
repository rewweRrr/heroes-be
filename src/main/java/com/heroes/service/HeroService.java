package com.heroes.service;

import com.heroes.models.CreateHeroDto;
import com.heroes.models.HeroDto;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

public interface HeroService {
    HeroDto createHero(CreateHeroDto hero);
    List<HeroDto> getHeroes();
    Stream<HeroDto> getHeroesAsStream();
    HeroDto getHero(UUID id);
}
