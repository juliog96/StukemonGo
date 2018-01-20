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
public class Pokemon {

    private String nombre;
    private String tipo;
    private int pc;
    private int vida;
    private String lugar;

    public Pokemon() {
    }

    public Pokemon(String nombre) {
        this.nombre = nombre;
    }

    public Pokemon(String nombre, String tipo, int pc, int vida, String lugar) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.pc = pc;
        this.vida = vida;
        this.lugar = lugar;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getPc() {
        return pc;
    }

    public void setPc(int pc) {
        this.pc = pc;
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    @Override
    public String toString() {
        return "Pokemon{" + "nombre=" + nombre + ", tipo=" + tipo + ", pc=" + pc + ", vida=" + vida + ", lugar=" + lugar + '}';
    }

}
