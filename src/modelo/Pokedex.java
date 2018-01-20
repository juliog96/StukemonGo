/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.time.LocalDate;

/**
 *
 * @author julio
 */
public class Pokedex {

    public int id;
    public Pokemon pokemon;
    public LocalDate fecha;
    public int pc;
    public int maximaVida;
    public int vidaActual;

    public Pokedex(int id, Pokemon pokemon, LocalDate fecha, int pc, int maximaVida, int vidaActual) {
        this.id = id;
        this.pokemon = pokemon;
        this.fecha = fecha;
        this.pc = pc;
        this.maximaVida = maximaVida;
        this.vidaActual = vidaActual;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Pokemon getPokemon() {
        return pokemon;
    }

    public void setPokemon(Pokemon pokemon) {
        this.pokemon = pokemon;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public int getPc() {
        return pc;
    }

    public void setPc(int pc) {
        this.pc = pc;
    }

    public int getMaximaVida() {
        return maximaVida;
    }

    public void setMaximaVida(int maximaVida) {
        this.maximaVida = maximaVida;
    }

    public int getVidaActual() {
        return vidaActual;
    }

    public void setVidaActual(int vidaActual) {
        this.vidaActual = vidaActual;
    }

}
