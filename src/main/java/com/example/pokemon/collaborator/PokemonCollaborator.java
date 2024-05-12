package com.example.pokemon.collaborator;

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

    private EvolutionPokemonSpecies getNextEvolution(String pokemonName, EvolvesTo evolvesTo) {
        if (evolvesTo.getSpecies() != null && evolvesTo.getSpecies().getName().equalsIgnoreCase(pokemonName)) {
            return evolvesTo.getEvolves_to().stream()
                    .findFirst()
                    .map(EvolvesTo::getSpecies)
                    .orElse(null);
        } else {
            return evolvesTo.getEvolves_to().stream()
                    .map(nextEvolvesTo -> {
                        return getNextEvolution(pokemonName, nextEvolvesTo);
                    })
                    .findFirst()
                    .orElse(null);
        }
    }

    public Pokemon getPokemonByNameOrID(String idOrName) {
        String url = "https://pokeapi.co/api/v2/pokemon/" + idOrName;
        return restTemplate.getForObject(url, Pokemon.class);
    }

    public Pokemon evolvePokemon(String idOrName) {

        PokemonSpecies pokemonSpecies = restTemplate
                .getForObject("https://pokeapi.co/api/v2/pokemon-species/" + idOrName, PokemonSpecies.class);

        EvolutionResponse evolutionResponse = restTemplate.getForObject(pokemonSpecies.getEvolution_chain().getUrl(),
                EvolutionResponse.class);

        if (evolutionResponse != null
                && evolutionResponse.getChain().getSpecies().getName().equals(pokemonSpecies.getName())) {
            EvolutionPokemonSpecies nextEvolution = evolutionResponse.getChain().getEvolves_to().get(0).getSpecies();
            return getPokemonByNameOrID(nextEvolution.getName());
        } else {
            if (evolutionResponse.getChain().getEvolves_to() != null) {
                EvolutionPokemonSpecies nextEvolution = getNextEvolution(pokemonSpecies.getName(),
                        evolutionResponse.getChain().getEvolves_to().get(0));
                if (nextEvolution != null) {
                    return getPokemonByNameOrID(nextEvolution.getName());
                }
                return null;
            } else {
                return null;
            }
        }
    }
}
