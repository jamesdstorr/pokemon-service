package com.example.pokemon.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.pokemon.model.Pokemon;
import com.example.pokemon.service.PokemonService;
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
    public ResponseEntity<?> getPokemonByName(@PathVariable("name") String name){ // Handles HTTP GET requests and returns a ResponseEntity containing a Pokemon object. 
        Pokemon pokemon = pokemonService.getPokemon(name); 
        if(pokemon == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Did you make a spelling mistake, dumbass?");
        }
        return ResponseEntity.ok(pokemon); 
    }

   @GetMapping("/pokemon/evolve/{name}")
    public ResponseEntity<?> getPokemonEvolution(@PathVariable("name") String name) {
        List<Pokemon> pokemon = pokemonService.evolvePokemon(name);
        if(pokemon.size() == 0){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("That Pokemon can't evolve, dumbass!");
        }
        return ResponseEntity.ok().body(pokemon);  
    }
}
