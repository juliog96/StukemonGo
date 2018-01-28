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
public class RankingUsuarios {
    
    private int ranking;
    private String nombreUser;
    private int numPokemon;

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public String getNombreUser() {
        return nombreUser;
    }

    public void setNombreUser(String nombreUser) {
        this.nombreUser = nombreUser;
    }

    public int getNumPokemon() {
        return numPokemon;
    }

    public void setNumPokemon(int numPokemon) {
        this.numPokemon = numPokemon;
    }

    @Override
    public String toString() {
        return "RankingUsuarios{" + "ranking=" + ranking + ", nombreUser=" + nombreUser + ", numPokemon=" + numPokemon + '}';
    }
    
    
    
}
