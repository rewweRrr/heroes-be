package com.heroes.service;

import com.heroes.entities.HeroEntity;
import com.heroes.mappers.HeroMapper;
import com.heroes.models.CreateHeroDto;
import com.heroes.models.HeroDto;
import com.heroes.repositories.HeroRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class HeroServiceImpl implements HeroService {

    @Inject
    HeroStorageService heroStorageService;
    @Inject
    HeroMapper heroMapper;

    @Inject
    HeroRepository heroRepository;

    @Override
    public HeroDto createHero(CreateHeroDto createHeroDto) {
        // TODO add validations;
        HeroEntity heroEntity = heroRepository.createHero(createHeroDto);
        return heroMapper.from(heroEntity);
//        heroStorageService.addHero(hero);
//        return hero;
    }

    @Override
    public List<HeroDto> getHeroes() {
        List<HeroEntity> heroEntities = heroRepository.findAllHeroes();
        return heroMapper.from(heroEntities);
//        return heroStorageService.getHeroes();
    }

    @Override
    public HeroDto getHero(UUID id) {
        return heroStorageService.getHero(id);
    }
}
