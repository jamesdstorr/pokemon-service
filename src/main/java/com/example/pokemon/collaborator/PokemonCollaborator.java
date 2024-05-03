package com.example.pokemon.collaborator;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.pokemon.model.Pokemon;
import com.example.pokemon.model.PokemonSpecies;
import com.example.pokemon.model.Evolution.EvolutionPokemonSpecies;
import com.example.pokemon.model.Evolution.EvolutionResponse;
import com.example.pokemon.model.Evolution.EvolvesTo;
import com.fasterxml.jackson.databind.ObjectMapper;






@Service
public class PokemonCollaborator {

    public EvolutionPokemonSpecies getNextEvolution(String pokemonName, EvolvesTo evolvesTo ){

        if(evolvesTo.getSpecies().getName().equalsIgnoreCase(pokemonName)){
            if(!evolvesTo.getEvolves_to().isEmpty()){
                return evolvesTo.getEvolves_to().get(0).getSpecies();
            }
            return null;
        }
        else{
            for(EvolvesTo nextEvolvesTo : evolvesTo.getEvolves_to()){
                EvolutionPokemonSpecies pokemonSpecies = getNextEvolution(pokemonName, nextEvolvesTo );
                if (pokemonSpecies != null) {
                    return pokemonSpecies;
                }
            }
        }
        return null;
    }



    private final RestTemplate restTemplate = new RestTemplate();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public Pokemon getPokemonByNameOrID(String idOrName) {
        String url = "https://pokeapi.co/api/v2/pokemon/" + idOrName;
        return restTemplate.getForObject(url, Pokemon.class);
    }

    public Pokemon evolvePokemon(String idOrName) {

        PokemonSpecies pokemonSpecies = restTemplate
                .getForObject("https://pokeapi.co/api/v2/pokemon-species/" + idOrName, PokemonSpecies.class);

        final String pokemonName = pokemonSpecies.getName();
 
        EvolutionResponse evolutionResponse = restTemplate.getForObject(pokemonSpecies.getEvolution_chain().getUrl(),
                EvolutionResponse.class);

        if(evolutionResponse!=null && evolutionResponse.getChain().getSpecies().getName().equals(pokemonName)){
            EvolutionPokemonSpecies nextEvolution = evolutionResponse.getChain().getEvolves_to().get(0).getSpecies();
            System.out.println(nextEvolution.getName());
            return getPokemonByNameOrID(nextEvolution.getName());
        }
        else {
            EvolvesTo evolvesTo = evolutionResponse.getChain().getEvolves_to().get(0);
            String nextEvolution = getNextEvolution(pokemonName, evolvesTo).getName();
            System.out.println(nextEvolution);
            return getPokemonByNameOrID(nextEvolution);
        }

    }

}


