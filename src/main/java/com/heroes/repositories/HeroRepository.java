package com.heroes.repositories;

import com.heroes.entities.HeroEntity;
import com.heroes.models.CreateHeroDto;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.stream.Stream;

@ApplicationScoped
public class HeroRepository implements PanacheRepository<HeroEntity> {


    @Transactional
    public HeroEntity createHero(CreateHeroDto hero) {
        HeroEntity heroEntity = new HeroEntity();
        heroEntity.setName(hero.getName());
        heroEntity.setDescription(hero.getDescription());

        persist(heroEntity);
        return heroEntity;
    }

    public List<HeroEntity> findAllHeroes() {
        return findAll().list();
    }

    public Stream<HeroEntity> findAllHeroesAsStream() {
        return findAll().stream();
    }
}
