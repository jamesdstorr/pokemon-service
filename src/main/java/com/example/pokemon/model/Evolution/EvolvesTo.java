package com.example.pokemon.model.Evolution;

import java.util.List;

import lombok.Data;

@Data
public class EvolvesTo {
    private EvolutionPokemonSpecies species;
    private List<EvolvesTo> evolves_to;
}
