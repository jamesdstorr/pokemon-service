package com.example.pokemon;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.pokemon.collaborator.PokemonCollaborator;
import com.example.pokemon.model.Pokemon;
import com.example.pokemon.service.PokemonServiceImpl;

@ExtendWith(MockitoExtension.class)
public class PokemonServiceTest {
    @Mock
    private PokemonCollaborator pokemonCollaborator;

    @InjectMocks
    private PokemonServiceImpl pokemonService;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void givenMockingIsDone_whenGetPokemonIsCalled_ShouldReturnPokemon() throws Exception {
        String idOrName = "pikachu";
        Pokemon expectedPokemon = Pokemon.builder().id(1).name("pikachu").build();

        Mockito
                .when(pokemonCollaborator.getPokemonByNameOrID(idOrName))
                .thenReturn(expectedPokemon);

        assertEquals(expectedPokemon.getName(), pokemonService.getPokemon(idOrName).getName());
    }

    @Test
    public void testEvolvePokemon() throws Exception {

        // Given
        String idOrName = "pikachu";
        List<Pokemon> expectedEvolvedPokemons = new ArrayList<Pokemon>();
        expectedEvolvedPokemons.add(Pokemon.builder().id(2).name("raichu").build());

        // When
        Mockito
                .when(pokemonCollaborator.evolvePokemon(idOrName))
                .thenReturn(expectedEvolvedPokemons);

        // Then
        assertEquals(expectedEvolvedPokemons.get(0).getName(),pokemonService.evolvePokemon(idOrName).get(0).getName());
    }

}
