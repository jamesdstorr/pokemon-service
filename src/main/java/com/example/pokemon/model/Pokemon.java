package com.example.pokemon.model;

import java.util.List;
import lombok.Data;

@Data
public class Pokemon {
    private String name;
    private int id;
    private int height;
    private int weight;
    private int base_experience;
    private List<AbilityItem> abilities;
}
