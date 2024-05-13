package com.example.pokemon.collaborator;

import java.util.Optional;
import java.util.function.Function;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.example.pokemon.model.Pokemon;
import com.example.pokemon.model.PokemonSpecies;
import com.example.pokemon.model.Evolution.EvolutionPokemonSpecies;
import com.example.pokemon.model.Evolution.EvolutionResponse;
import com.example.pokemon.model.Evolution.EvolvesTo;

@Component
public class PokemonCollaborator {
    private final RestTemplate restTemplate;

    public PokemonCollaborator(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private Optional<EvolutionPokemonSpecies> getNextEvolution(String pokemonName, EvolvesTo evolvesTo) {
        if(evolvesTo.getEvolves_to().isEmpty()){
            return Optional.empty();
        }
        if (evolvesTo.getSpecies() != null &&
                evolvesTo.getSpecies().getName().equalsIgnoreCase(pokemonName)) {
            return evolvesTo.getEvolves_to().stream()
                    .findFirst()
                    .map(EvolvesTo::getSpecies)
                    .map(Optional::ofNullable)
                    .orElse(Optional.empty());
        } else {
            return evolvesTo.getEvolves_to().stream()
                    .map(nextEvolvesTo -> {                        
                        return getNextEvolution(pokemonName, nextEvolvesTo);
                    })
                    .filter(Optional::isPresent)
                    .findFirst()
                    .flatMap(Function.identity());
        }

    }

    // Get Pokemon by ID or Name
    public Pokemon getPokemonByNameOrID(String idOrName) {
        String url = "https://pokeapi.co/api/v2/pokemon/" + idOrName;
        return restTemplate.getForObject(url, Pokemon.class);
    }

    // Get Evolutions of a Pokemon
    public Pokemon evolvePokemon(String idOrName) {

        PokemonSpecies pokemonSpecies = restTemplate
                .getForObject("https://pokeapi.co/api/v2/pokemon-species/" + idOrName, PokemonSpecies.class);

        EvolutionResponse evolutionResponse = restTemplate.getForObject(pokemonSpecies.getEvolution_chain().getUrl(),
                EvolutionResponse.class);

        if (evolutionResponse.getChain().getSpecies().getName().equals(pokemonSpecies.getName())) {
            EvolutionPokemonSpecies nextEvolution = evolutionResponse.getChain().getEvolves_to().get(0).getSpecies();
            return getPokemonByNameOrID(nextEvolution.getName());
        } else {
            return evolutionResponse.getChain().getEvolves_to().stream()                    
                    .map(nextEvolution -> getNextEvolution(pokemonSpecies.getName(),nextEvolution))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .map(evolutionPokemonSpecies -> getPokemonByNameOrID(evolutionPokemonSpecies.getName()))                    
                    .findFirst()
                    .orElse(null);
        }
    }
}
