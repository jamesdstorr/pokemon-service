package com.example.pokemon.model;

import lombok.Data;

@Data
public class Ability {
    private boolean is_hidden;
    private int slot;
    private AbilityDetail ability;
}
