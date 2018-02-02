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
public class RankingPokemon {

    private int ranking;
    private int numVictorias;
    private String nombre;
    private int pc;
    private int vida;
    private String nombreUser;

    public RankingPokemon() {
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public int getNumVictorias() {
        return numVictorias;
    }

    public void setNumVictorias(int numVictorias) {
        this.numVictorias = numVictorias;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public String getNombreUser() {
        return nombreUser;
    }

    public void setNombreUser(String nombreUser) {
        this.nombreUser = nombreUser;
    }

    @Override
    public String toString() {
        return "RankingPokemon{" + "ranking=" + ranking + ", numVictorias=" + numVictorias + ", nombre=" + nombre + ", pc=" + pc + ", vida=" + vida + ", nombreUser=" + nombreUser + '}';
    }

}
