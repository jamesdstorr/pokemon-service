package com.example.pokemon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pokemon.collaborator.PokemonCollaborator;
import com.example.pokemon.model.Pokemon;


@Service
public class PokemonServiceImpl implements PokemonService {
    
    @Autowired
    private PokemonCollaborator pokemonCollab;

   
    public PokemonServiceImpl(PokemonCollaborator pokemonCollab){
        this.pokemonCollab = pokemonCollab;
    }

    public Pokemon getPokemon(String name){
        return pokemonCollab.getPokemonByNameOrID(name);
    }

    public Pokemon evolvePokemon(String name){
        return pokemonCollab.evolvePokemon(name);
    }
}
