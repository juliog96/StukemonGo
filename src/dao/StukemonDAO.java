/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import excepciones.Excepcion;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import modelo.Pokemon;
import modelo.Pokeparada;
import modelo.Usuario;

/**
 *
 * @author julio
 */
public class StukemonDAO {

    private Connection conexion;

    // ********************* Selects ****************************
    public Usuario getUsuarioByNombre(String nombre) throws SQLException, Excepcion {
        // Creamos usuario para comprobar si existe
        Usuario usu = new Usuario(nombre);
        if (!existeUsuario(usu)) {
            throw new Excepcion("ERROR: No existe ningún usuario con ese nombre");
        }
        String select = "select * from user where username='" + nombre + "'";
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery(select);
        Usuario user = new Usuario();
        // Como sólo habrá un resultado no hace falta while
        if (rs.next()) {
            user.setNombreuser(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setPokeballs(rs.getInt("pokeballs"));
            user.setPociones(rs.getInt("potions"));
            user.setNivel(rs.getInt("level"));
            user.setLugar(rs.getString("place"));
            user.setMonedas(rs.getInt("pokecoins"));
            user.setPuntos(rs.getInt("points"));
        }
        rs.close();
        st.close();
        return user;
    }

    public Pokemon getPokemonByNombre(String nombre) throws SQLException, Excepcion {
        // Creamos usuario para comprobar si existe
        Pokemon pk = new Pokemon(nombre);
        if (!existePokemon(pk)) {
            throw new Excepcion("ERROR: No existe ningún pokemon con ese nombre");
        }
        String select = "select * from pokemon where name='" + nombre + "'";
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery(select);
        Pokemon poke = new Pokemon();
        // Como sólo habrá un resultado no hace falta while
        if (rs.next()) {
            poke.setNombre(rs.getString("name"));
            poke.setTipo(rs.getString("type"));
            poke.setPc(rs.getInt("pc"));
            poke.setVida(rs.getInt("life"));
            poke.setLugar(rs.getString("place"));
        }
        rs.close();
        st.close();
        return poke;
    }

    public boolean validarPassword(String nombre, String pass) throws SQLException, Excepcion {
        Usuario usu = new Usuario(nombre);
        boolean contraseña = false;
        if (!existeUsuario(usu)) {
            throw new Excepcion("ERROR: Nombre de usuario erroneo");
        }
        String select = "select password from user where username='" + nombre + "'";
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery(select);
        if (rs.next()) {
            String passcif = rs.getString("password");
            String passdes;
            try {
                //Desincriptamos la contraseña de la base de datos
                passdes = Utilidades.Contraseña.Desencriptar(passcif);
                if (pass.equalsIgnoreCase(passdes)) {
                    contraseña = true;
                    System.out.println("Contraseña: " + pass);
                    System.out.println("Contraseña Base Datos: " + passdes);
                }
            } catch (Exception ex) {
                throw new Excepcion("ERROR: Contraseña no coinciden");
            }
        }
        return contraseña;
    }

    public List<Pokemon> getPokemonByUserPlace(String nombre) throws SQLException, Excepcion {
        // Creamos usuario para comprobar si existe
        List<Pokemon> pokemon = new ArrayList<>();
        Usuario usu = new Usuario(nombre);
        if (!existeUsuario(usu)) {
            throw new Excepcion("ERROR: No existe ningún usuario con ese nombre");
        }
        String select = "select pokemon.name, pokemon.type, pokemon.pc, pokemon.life, pokemon.place, "
                + "user.username, user.place  from pokemon inner join user on pokemon.place = user.place "
                + "where pokemon.place = (select place from user where username = 'Julio');";
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery(select);
        String nombreUser = "";
        String lugarUser = "";
        if (rs.first()) {//recorre el resultset al siguiente registro si es que existen
            rs.beforeFirst();//regresa el puntero al primer registro
            while (rs.next()) {
                Pokemon poke = new Pokemon();
                poke.setNombre(rs.getString("pokemon.name"));
                poke.setTipo(rs.getString("pokemon.type"));
                poke.setPc(rs.getInt("pokemon.pc"));
                poke.setVida(rs.getInt("pokemon.life"));
                poke.setLugar(rs.getString("pokemon.place"));
                pokemon.add(poke);
                nombreUser = (rs.getString("user.username"));
                lugarUser = (rs.getString("user.place"));
            }
            System.out.println("Nombre: " + nombreUser);
            System.out.println("Lugar: " + lugarUser);
        } else {
            throw new Excepcion("ERROR: No hay ningun pokemon en el lugar del usuario");
        }
        rs.close();
        st.close();
        return pokemon;
    }

    // ********************* Inserts ****************************
    // Función que da de alta un usuario 
    public void insertarUsuario(Usuario usu) throws SQLException, Excepcion {

        if (existeUsuario(usu)) {
            throw new Excepcion("ERROR: Ya existe un usuario con ese nombreuser");
        } else {
            String insert = "insert into user values (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conexion.prepareStatement(insert);
            ps.setString(1, usu.getNombreuser());
            //Ciframos la contraseña
            String passcif = Utilidades.Contraseña.Encriptar(usu.getPassword());
            ps.setString(2, passcif);
            ps.setInt(3, usu.getPokeballs());
            ps.setInt(4, usu.getPociones());
            ps.setInt(5, usu.getNivel());
            ps.setString(6, usu.getLugar());
            ps.setInt(7, usu.getMonedas());
            ps.setInt(8, usu.getPuntos());
            ps.executeUpdate();
            ps.close();
        }
    }

    // Función que da de alta un pokemon 
    public void insertarPokemon(Pokemon pk) throws SQLException, Excepcion {

        if (existePokemon(pk)) {
            throw new Excepcion("ERROR: Ya existe un pokemon con ese nombre");
        } else {
            String insert = "insert into pokemon values (?, ?, ?, ?, ?)";
            PreparedStatement ps = conexion.prepareStatement(insert);
            ps.setString(1, pk.getNombre());
            ps.setString(2, pk.getTipo());
            ps.setInt(3, pk.getPc());
            ps.setInt(4, pk.getVida());
            ps.setString(5, pk.getLugar());
            ps.executeUpdate();
            ps.close();
        }
    }

    // Función que da de alta una pokeparada 
    public void insertarPokeparada(Pokeparada popa) throws SQLException, Excepcion {

        if (existePokeparada(popa)) {
            throw new Excepcion("ERROR: Ya existe una pokeparada con ese nombre");
        } else if (existePokeparadaLugar(popa)) {
            throw new Excepcion("ERROR: Ya existe una pokeparada en ese lugar");
        } else {
            String insert = "insert into pokeparada values (?, ?, ?, ?)";
            PreparedStatement ps = conexion.prepareStatement(insert);
            ps.setString(1, popa.getNombre());
            ps.setString(2, popa.getLugar());
            ps.setInt(3, popa.getPokeballs());
            ps.setInt(4, popa.getPociones());
            ps.executeUpdate();
            ps.close();
        }
    }

    // ********************* Updates ******************************************
    public void modificarLugarUsuario(Usuario usu) throws SQLException, Excepcion {
        if (!existeUsuario(usu)) {
            throw new Excepcion("ERROR: No existe ningún usuario con ese nombreuser");
        }
        String update = "update user set place=? where username=?";
        PreparedStatement ps = conexion.prepareStatement(update);
        ps.setString(1, usu.getLugar());
        ps.setString(2, usu.getNombreuser());
        ps.executeUpdate();
        ps.close();
    }

    public void modificarLugarPokemon(Pokemon pk) throws SQLException, Excepcion {
        if (!existePokemon(pk)) {
            throw new Excepcion("ERROR: No existe ningún pokemon con ese nombre");
        }
        String update = "update pokemon set place=? where name=?";
        PreparedStatement ps = conexion.prepareStatement(update);
        ps.setString(1, pk.getLugar());
        ps.setString(2, pk.getNombre());
        ps.executeUpdate();
        ps.close();
    }

    // ********************* Funciones adicionales ****************************
    private boolean existeUsuario(Usuario usu) throws SQLException {
        String select = "select * from user where username='" + usu.getNombreuser() + "'";
        Statement st = conexion.createStatement();
        boolean existe = false;
        ResultSet rs = st.executeQuery(select);
        if (rs.next()) {
            existe = true;
        }
        rs.close();
        st.close();
        return existe;
    }

    private boolean existePokemon(Pokemon pk) throws SQLException {
        String select = "select * from pokemon where name='" + pk.getNombre() + "'";
        Statement st = conexion.createStatement();
        boolean existe = false;
        ResultSet rs = st.executeQuery(select);
        if (rs.next()) {
            existe = true;
        }
        rs.close();
        st.close();
        return existe;
    }

    private boolean existePokeparada(Pokeparada popa) throws SQLException {
        String select = "select * from pokeparada where name='" + popa.getNombre() + "'";
        Statement st = conexion.createStatement();
        boolean existe = false;
        ResultSet rs = st.executeQuery(select);
        if (rs.next()) {
            existe = true;
        }
        rs.close();
        st.close();
        return existe;
    }

    private boolean existePokeparadaLugar(Pokeparada popa) throws SQLException {
        String select = "select * from pokeparada where place='" + popa.getLugar() + "'";
        Statement st = conexion.createStatement();
        boolean existe = false;
        ResultSet rs = st.executeQuery(select);
        if (rs.next()) {
            existe = true;
        }
        rs.close();
        st.close();
        return existe;
    }

    // ********************* Conectar / Desconectar ****************************
    public void conectar() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/stukemongo";
        String user = "root";
        String pass = "";
        conexion = DriverManager.getConnection(url, user, pass);
    }

    public void desconectar() throws SQLException {
        if (conexion != null) {
            conexion.close();
        }
    }
}
