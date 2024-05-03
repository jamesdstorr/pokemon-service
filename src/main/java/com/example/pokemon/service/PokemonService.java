package com.example.pokemon.service;

import com.example.pokemon.model.Pokemon;

public interface PokemonService {
    Pokemon getPokemon(String name);

    Pokemon evolvePokemon(String name);
}
