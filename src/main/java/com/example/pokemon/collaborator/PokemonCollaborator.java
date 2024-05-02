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
        String speciesUrl = "https://pokeapi.co/api/v2/pokemon-species/" + idOrName;
        PokemonSpecies species = restTemplate.getForObject(speciesUrl, PokemonSpecies.class);

        List<PokemonEvolution> evolutions = new ArrayList<>();
        for (PokemonEvolution evolution : species.getEvolutions()){
            evolutions.add(restTemplate.getForObject("https://pokeapi.co/api/v2/evolution/" + evolution.getId(), PokemonEvolution.class));
        }

        return evolutions.stream()
                .filter(PokemonEvolution::isChain)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No chain found for " + idOrName))
                .getTarget();
    }


        // This method gets a Pokémon object from the PokeAPI using either its name or ID.
        // If an invalid name or ID is passed, it throws a RuntimeException.
        public Pokemon getPokemonByNameOrID(String idOrName){
            String url = "https://pokeapi.co/api/v2/pokemon/" + idOrName;
            return restTemplate.getForObject(url, Pokemon.class);
        }

        // This method gets a Pokémon object from the PokeAPI using its name or ID.
        // If an invalid name or ID is passed, it throws a RuntimeException.
        public Pokemon evolvePokemon(String idOrName){
            String speciesUrl = "https://pokeapi.co/api/v2/pokemon-species/" + idOrName;
            PokemonSpecies species = restTemplate.getForObject(speciesUrl, PokemonSpecies.class);

            List<PokemonEvolution> evolutions = new ArrayList<>();
            for (PokemonEvolution evolution : species.getEvolutions()){
                evolutions.add(restTemplate.getForObject("https://pokeapi.co/api/v2/evolution/" + evolution.getId(), PokemonEvolution.class));
            }

            return evolutions.stream()
                    .filter(PokemonEvolution::isChain)
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("No chain found for " + idOrName))
                    .getTarget();
        }

        //TODO Something Something Something 

}
