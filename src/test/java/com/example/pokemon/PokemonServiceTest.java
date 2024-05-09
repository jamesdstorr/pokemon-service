package com.example.pokemon;

import static org.junit.Assert.assertEquals;

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
        Pokemon expectedPokemon = new Pokemon();
        expectedPokemon.setName("pikachu");
        expectedPokemon.setId(150);
        expectedPokemon.setHeight(4);
        expectedPokemon.setWeight(60);
        expectedPokemon.setBase_experience(150);

        Mockito
                .when(pokemonCollaborator.getPokemonByNameOrID(idOrName))
                .thenReturn(expectedPokemon);

        assertEquals(expectedPokemon.getName(), pokemonService.getPokemon(idOrName).getName());
    }

}
