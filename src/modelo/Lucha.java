/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author julio
 */
public class Lucha {

    public int id;
    public Pokedex pokemon1;
    public Pokedex pokemon2;
    public Pokedex ganador;

    public Lucha(int id, Pokedex pokemon1, Pokedex pokemon2, Pokedex ganador) {
        this.id = id;
        this.pokemon1 = pokemon1;
        this.pokemon2 = pokemon2;
        this.ganador = ganador;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Pokedex getPokemon1() {
        return pokemon1;
    }

    public void setPokemon1(Pokedex pokemon1) {
        this.pokemon1 = pokemon1;
    }

    public Pokedex getPokemon2() {
        return pokemon2;
    }

    public void setPokemon2(Pokedex pokemon2) {
        this.pokemon2 = pokemon2;
    }

    public Pokedex getGanador() {
        return ganador;
    }

    public void setGanador(Pokedex ganador) {
        this.ganador = ganador;
    }

    @Override
    public String toString() {
        return "Lucha{" + "id=" + id + ", pokemon1=" + pokemon1 + ", pokemon2=" + pokemon2 + ", ganador=" + ganador + '}';
    }

}
