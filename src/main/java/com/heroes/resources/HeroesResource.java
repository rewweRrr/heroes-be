package com.heroes.resources;

import com.heroes.models.CreateHeroDto;
import com.heroes.models.HeroDto;
import com.heroes.service.HeroService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;
import java.util.UUID;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/hero")
public class HeroesResource {

    @Inject
    HeroService heroService;

    @GET
    public List<HeroDto> heroes() {
        return heroService.getHeroes();
    }


    @POST
    public HeroDto createHero(CreateHeroDto createHeroDto) {
        return heroService.createHero(createHeroDto);
    }

    @GET
    @Path("/{id}")
    public HeroDto hero(@PathParam("id") UUID id) {
        return heroService.getHero(id);
    }
}
