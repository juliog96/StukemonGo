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
public class Pokeparada {
    
    private String nombre;
    private String lugar;
    private Integer pokeballs;
    private Integer pociones;

    public Pokeparada(String nombre, String lugar, Integer pokeballs, Integer pociones) {
        this.nombre = nombre;
        this.lugar = lugar;
        this.pokeballs = pokeballs;
        this.pociones = pociones;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public Integer getPokeballs() {
        return pokeballs;
    }

    public void setPokeballs(Integer pokeballs) {
        this.pokeballs = pokeballs;
    }

    public Integer getPociones() {
        return pociones;
    }

    public void setPociones(Integer pociones) {
        this.pociones = pociones;
    }

    @Override
    public String toString() {
        return "Pokeparada{" + "nombre=" + nombre + ", lugar=" + lugar + ", pokeballs=" + pokeballs + ", pociones=" + pociones + '}';
    }   
}
