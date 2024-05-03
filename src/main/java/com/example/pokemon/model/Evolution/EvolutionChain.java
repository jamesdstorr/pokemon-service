package com.example.pokemon.model.Evolution;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class EvolutionChain {

    @JsonProperty("evolves_to")
    private List<EvolvesTo> evolves_to;
    private EvolutionPokemonSpecies species;
}
