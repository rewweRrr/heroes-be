package com.heroes.service;

import com.heroes.models.HeroDto;

import java.util.List;
import java.util.UUID;

public interface HeroStorageService {
    List<HeroDto> getHeroes();

    HeroDto addHero(HeroDto hero);

    HeroDto getHero(UUID id);
}
