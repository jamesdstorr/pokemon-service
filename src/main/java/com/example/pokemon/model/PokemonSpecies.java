package com.example.pokemon.model;

import lombok.Data;

@Data
public class PokemonSpecies {
    private String name;
    private EvolutionChain evolution_chain;
}