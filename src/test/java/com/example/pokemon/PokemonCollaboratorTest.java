package com.example.pokemon;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import com.example.pokemon.collaborator.PokemonCollaborator;
import com.example.pokemon.model.Pokemon;

import static org.junit.Assert.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class PokemonCollaboratorTest {

    @InjectMocks
    private PokemonCollaborator pokemonCollaborator;

    @Mock
    private RestTemplate restTemplate;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetPokemonByNameOrID() throws Exception {
        // Given
        String idOrName = "pikachu";
        Pokemon expectedPokemon = new Pokemon();
        expectedPokemon.setName("pikachu");
        expectedPokemon.setId(150);
        expectedPokemon.setHeight(4);
        expectedPokemon.setWeight(60);
        expectedPokemon.setBase_experience(150);

        // When
        Mockito
            .when(restTemplate.getForObject("https://pokeapi.co/api/v2/pokemon/" + idOrName, Pokemon.class))
                    .thenReturn(expectedPokemon);

        // Then
        assertEquals(expectedPokemon.getName(), pokemonCollaborator.getPokemonByNameOrID(idOrName).getName());
    }

}
