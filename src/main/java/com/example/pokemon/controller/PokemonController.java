package com.example.pokemon.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.pokemon.model.Pokemon;
import com.example.pokemon.service.PokemonService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;




@RestController // Indicates that this class is a REST controller, which means it handles HTTP requests and returns responses.
public class PokemonController {
    
    @Autowired
    private PokemonService pokemonService; // Injects an instance of PokemonService into this controller, making it available for use in methods like getPokemonByName().
    @Autowired
    private ObjectMapper objectMapper;

    public PokemonController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    @GetMapping("/pokemon/{name}") // Maps incoming HTTP GET requests with a URI matching /pokemon/{name} to the getPokemonByName() method.
    public ResponseEntity<Pokemon> getPokemonByName(@PathVariable("name") String name){ // Handles HTTP GET requests and returns a ResponseEntity containing a Pokemon object. 
        Pokemon pokemon = pokemonService.getPokemon(name); // Declares a local variable pokemon of type Pokemon within the getPokemonByName() method.
        return ResponseEntity.ok(pokemon); // Returns a ResponseEntity with an HTTP status code of 200 (OK) and a response body containing the Pokemon object.
    }

   @GetMapping("/pokemon/evolve/{name}")
    public ResponseEntity<List<Pokemon>> getPokemonEvolution(@PathVariable("name") String name) {
        List<Pokemon> pokemon = pokemonService.evolvePokemon(name); // Declares a local variable pokemon of type Pokemon within the getPokemonByName() method.
        return ResponseEntity.ok().body(pokemon);  
    }

}
