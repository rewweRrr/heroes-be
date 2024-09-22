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
import java.util.stream.Stream;

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
    }

    @Override
    public List<HeroDto> getHeroes() {
        List<HeroEntity> heroEntities = heroRepository.findAllHeroes();
        return heroMapper.from(heroEntities);
    }

    @Override
    public Stream<HeroDto> getHeroesAsStream() {
        Stream<HeroEntity> heroEntitiesStream = heroRepository.findAllHeroesAsStream();
        return heroEntitiesStream.map(heroMapper::from);
    }

    @Override
    public HeroDto getHero(UUID id) {
        return heroStorageService.getHero(id);
    }
}
