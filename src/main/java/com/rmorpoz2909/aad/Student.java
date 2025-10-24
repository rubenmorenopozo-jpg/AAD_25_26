package com.rmorpoz2909.aad;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data //Data nos genera getters, setters automáticamente.
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Student {
    private int id;
    private String nombre;
    private double nota;
}
