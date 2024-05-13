package com.example.pokemon.service;

import java.util.List;

import com.example.pokemon.model.Pokemon;

public interface PokemonService {
    Pokemon getPokemon(String name);

    List<Pokemon> evolvePokemon(String name);
}
