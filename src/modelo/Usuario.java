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
public class Usuario {

    private String nombreuser;
    private String password;
    private int pokeballs;
    private int pociones;
    private int nivel;
    private String lugar;
    private int monedas;
    private int puntos;

    public Usuario() {
    }

    public Usuario(String nombreuser) {
        this.nombreuser = nombreuser;
    }

    public Usuario(String nombreuser, String password, String lugar) {
        this.nombreuser = nombreuser;
        this.password = password;
        this.pokeballs = 20;
        this.pociones = 0;
        this.nivel = 0;
        this.lugar = lugar;
        this.monedas = 0;
        this.puntos = 0;
    }

    public String getNombreuser() {
        return nombreuser;
    }

    public void setNombreuser(String nombreuser) {
        this.nombreuser = nombreuser;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPokeballs() {
        return pokeballs;
    }

    public void setPokeballs(int pokeballls) {
        this.pokeballs = pokeballls;
    }

    public int getPociones() {
        return pociones;
    }

    public void setPociones(int pociones) {
        this.pociones = pociones;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public int getMonedas() {
        return monedas;
    }

    public void setMonedas(int monedas) {
        this.monedas = monedas;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    @Override
    public String toString() {
        return "Usuario{" + "nombreuser=" + nombreuser + ", password=" + password + ", pokeballs=" + pokeballs + ", pociones=" + pociones + ", nivel=" + nivel + ", lugar=" + lugar + ", monedas=" + monedas + ", puntos=" + puntos + '}';
    }

}
