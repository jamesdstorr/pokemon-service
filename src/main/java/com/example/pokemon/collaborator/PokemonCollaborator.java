package com.example.pokemon.collaborator;


import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.pokemon.model.Pokemon;

@Service
public class PokemonCollaborator {
    
    private final RestTemplate restTemplate = new RestTemplate();

    public Pokemon getPokemonByNameOrID(String idOrName){
        String url = "https://pokeapi.co/api/v2/pokemon/" + idOrName;
        return restTemplate.getForObject(url, Pokemon.class);
    }

    public Pokemon evolvePokemon(String idOrName){
        String url = "https://pokeapi.co/api/v2/pokemon-species/" + idOrName;
        //Get Species Record for ID
        //Get Evolution Chain ID from Species Record 
        //Use Evolution Chain ID to get Evolutions for Pokemon 
    }



}
