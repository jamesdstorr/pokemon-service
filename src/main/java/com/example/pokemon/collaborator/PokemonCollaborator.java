package com.example.pokemon.collaborator;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    // Get Pokemon by ID or Name
    public Pokemon getPokemonByNameOrID(String idOrName) {
        String url = "https://pokeapi.co/api/v2/pokemon/" + idOrName;
        return restTemplate.getForObject(url, Pokemon.class);
    }

    // Get Evolutions of a Pokemon
    public List<Pokemon> evolvePokemon(String idOrName) {

        PokemonSpecies pokemonSpecies = restTemplate
                .getForObject("https://pokeapi.co/api/v2/pokemon-species/" + idOrName, PokemonSpecies.class);
        if (pokemonSpecies == null)
            return Collections.emptyList();

        EvolutionResponse evolutionResponse = restTemplate.getForObject(pokemonSpecies.getEvolution_chain().getUrl(),
                EvolutionResponse.class);
        if (evolutionResponse == null)
            return Collections.emptyList();

        if (evolutionResponse.getChain().getSpecies().getName().equals(pokemonSpecies.getName())) {
            return evolutionResponse.getChain().getEvolves_to().stream()
                    .map(EvolvesTo::getSpecies)
                    .map(EvolutionPokemonSpecies::getName)
                    .map(this::getPokemonByNameOrID)
                    .collect(Collectors.toList());

        } else {
            for (EvolvesTo evolvesTo : evolutionResponse.getChain().getEvolves_to())
                if (evolvesTo != null)
                    return findEvolutionStage(pokemonSpecies.getName(), evolvesTo).orElse(Collections.emptyList())
                            .stream()
                            .map(EvolvesTo::getSpecies)
                            .map(EvolutionPokemonSpecies::getName)
                            .map(this::getPokemonByNameOrID)
                            .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    private Optional<List<EvolvesTo>> findEvolutionStage(String name, EvolvesTo currentLevel) {
        if (currentLevel.getSpecies().getName().equals(name)) {
            return Optional.ofNullable(currentLevel.getEvolves_to());
        } else {
            return currentLevel.getEvolves_to().stream()
                    .map(evolvesTo -> findEvolutionStage(name, evolvesTo))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .findFirst();

        }
    }

}
