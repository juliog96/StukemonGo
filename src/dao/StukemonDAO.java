/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import excepciones.Excepcion;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.*;
import java.util.ArrayList;
import java.util.List;
import modelo.Pokedex;
import modelo.Pokemon;
import modelo.Pokeparada;
import modelo.RankingPokemon;
import modelo.RankingUsuarios;
import modelo.Usuario;

/**
 *
 * @author julio
 */
public class StukemonDAO {

    private Connection conexion;

    public void capturarPokemon(Usuario usu, Pokemon pokemon) throws SQLException, Excepcion {
        if (!existeUsuario(usu)) {
            throw new Excepcion("ERROR: No existe ningún usuario con ese nombre");
        }
        if (!existePokemon(pokemon)) {
            throw new Excepcion("ERROR: No existe ningún pokemon con ese nombre");
        }
        if (!calcularPokeballs(usu)) {
            throw new Excepcion("ERROR: El usuario no tiene pokeballs");
        }
        if ((usu.getLugar().equals(pokemon.getLugar()))) {
            //Aqui le cambio el lugar pero cuando vuelva a iniciar el programa volvera a capturarlo 
            //porque cuando el pokemon se vuelve a crear remplaza a este lugar.
            pokemon.setLugar("Diagonal Mar");
            modificarLugarPokemon(pokemon);
            modificarPokeballsCaptura(usu);
            if (!usu.capturar()) {
                throw new Excepcion("·El pokemon ha escapado");
            } else {
                LocalDate fecha = LocalDate.now();
                Pokedex pokedex = new Pokedex(pokemon, fecha, pokemon.getPc(), pokemon.getVida(), pokemon.getVida());
                int valorEntero = (int) Math.floor(Math.random() * (9000 - 1000 + 1) + 1000);
                pokedex.setId(valorEntero);
                insertarPokedex(pokedex, usu);
                usu.getPokedex().add(pokedex);
            }
        } else {
            throw new Excepcion("ERROR: El usuario y el pokemon no estan en el mismo lugar");
        }
    }

    public void cogerRegalos(Usuario usu, Pokeparada pokeparada) throws SQLException, Excepcion {
        if (!existeUsuario(usu)) {
            throw new Excepcion("ERROR: No existe ningún usuario con ese nombre");
        }
        if (!existePokeparada(pokeparada)) {
            throw new Excepcion("ERROR: No existe ningúna pokeparada con ese nombre");
        }
        if (usu.getLugar().equals(pokeparada.getLugar())) {
            int pokeballsModificas = usu.getPokeballs() + pokeparada.getPokeballs();
            int potionsModificadas = usu.getPociones() + pokeparada.getPociones();
            usu.setPokeballs(pokeballsModificas);
            usu.setPociones(potionsModificadas);
            String update = "update user set potions=?, pokeballs=? where username=?";
            PreparedStatement ps = conexion.prepareStatement(update);
            ps.setInt(1, usu.getPociones());
            ps.setInt(2, usu.getPokeballs());
            ps.setString(3, usu.getNombreuser());
            ps.executeUpdate();
            ps.close();
            System.out.println("·Pokeballs y Potions modificadas");
        } else {
            throw new Excepcion("ERROR: Usuario y Pokeparada no estan en el mismo lugar ");
        }
    }

    //Este metodo se tiene que optimizar pero no me ha dado tiempo, lo siento Mar.
    public void lucha(Usuario usu1, Pokedex pokedex1, Usuario usu2, Pokedex pokedex2) throws SQLException, Excepcion {
        if (!existeUsuario(usu1)) {
            throw new Excepcion("ERROR: No existe ningún usuario con ese nombre");
        }
        if (!existePokedexByUser(pokedex1, usu1)) {
            throw new Excepcion("ERROR: El usuario no tiene el pokemon en su pokedex");
        }
        if (!existeUsuario(usu2)) {
            throw new Excepcion("ERROR: No existe ningún usuario con ese nombre");
        }
        if (!existePokedexByUser(pokedex2, usu2)) {
            throw new Excepcion("ERROR: El usuario no tiene el pokemon en su pokedex");
        }
        if (!usu1.getLugar().equals(usu2.getLugar())) {
            throw new Excepcion("ERROR: Los usuarios no estan en el mismo lugar");
        }
        int stucoins = (int) (Math.floor(Math.random() * (50 - 20 + 1) + 20));
        int points = (int) (Math.floor(Math.random() * (8 - 4 + 1) + 4));
        int puntos = 0;

        //****************OPCION 1***************************
        if (pokedex1.getVidaActual() > pokedex2.getVidaActual()) {
            while (pokedex1.getVidaActual() > 0 && pokedex2.getVidaActual() > 0) {
                int fuerzaAtaque = ((pokedex1.getPc() / 2) + ((int) (Math.floor(Math.random() * (4 - 1 + 1) + 1))));
                pokedex2.setVidaActual(pokedex2.getVidaActual() - fuerzaAtaque);
                if (pokedex2.getVidaActual() > 0) {
                    fuerzaAtaque = ((pokedex2.getPc() / 2) + ((int) (Math.floor(Math.random() * (4 - 1 + 1) + 1))));
                    pokedex1.setVidaActual(pokedex1.getVidaActual() - fuerzaAtaque);
                } else {
                    insertarLucha(pokedex1, pokedex2, pokedex1);
                    pokedex2.setVidaActual(0);

                    String actulizarVida = "update pokedex set lifecurrent=? where idpokedex=?";
                    PreparedStatement ac = conexion.prepareStatement(actulizarVida);
                    ac.setInt(1, pokedex1.getVidaActual());
                    ac.setInt(2, pokedex1.getId());
                    ac.executeUpdate();
                    ac.close();

                    String actulizarVida2 = "update pokedex set lifecurrent=0 where idpokedex=?";
                    PreparedStatement ac2 = conexion.prepareStatement(actulizarVida2);
                    ac2.setInt(1, pokedex2.getId());
                    ac2.executeUpdate();
                    ac2.close();

                    String update = "update user set pokecoins=(pokecoins+?) where username=?";
                    PreparedStatement ps = conexion.prepareStatement(update);
                    ps.setInt(1, stucoins);
                    ps.setString(2, usu1.getNombreuser());
                    ps.executeUpdate();
                    ps.close();

                    String select = "select points from user where username='" + usu1.getNombreuser() + "'";
                    Statement st = conexion.createStatement();
                    ResultSet rs = st.executeQuery(select);
                    if (rs.next()) {
                        puntos = (rs.getInt("points"));
                    }
                    rs.close();
                    st.close();

                    int puntosTotal = puntos + points;

                    if (puntosTotal > 15) {
                        String update3 = "update user set level=(level+1), points=0 where username=?";
                        PreparedStatement ps3 = conexion.prepareStatement(update3);
                        ps3.setString(1, usu1.getNombreuser());
                        ps3.executeUpdate();
                        ps3.close();
                    } else {
                        String update4 = "update user set points=? where username=?";
                        PreparedStatement ps4 = conexion.prepareStatement(update4);
                        ps4.setInt(1, puntosTotal);
                        ps4.setString(2, usu1.getNombreuser());
                        ps4.executeUpdate();
                        ps4.close();
                    }
                    System.out.println("El ganador es " + usu1.getNombreuser());
                }
                if (pokedex1.getVidaActual() <= 0) {
                    insertarLucha(pokedex1, pokedex2, pokedex2);
                    pokedex1.setVidaActual(0);

                    String actulizarVida = "update pokedex set lifecurrent=? where idpokedex=?";
                    PreparedStatement ac = conexion.prepareStatement(actulizarVida);
                    ac.setInt(1, pokedex2.getVidaActual());
                    ac.setInt(2, pokedex2.getId());
                    ac.executeUpdate();
                    ac.close();

                    String actulizarVida2 = "update pokedex set lifecurrent=0 where idpokedex=?";
                    PreparedStatement ac2 = conexion.prepareStatement(actulizarVida2);
                    ac2.setInt(1, pokedex1.getId());
                    ac2.executeUpdate();
                    ac2.close();

                    String update = "update user set pokecoins=(pokecoins+?) where username=?";
                    PreparedStatement ps = conexion.prepareStatement(update);
                    ps.setInt(1, stucoins);
                    ps.setString(2, usu2.getNombreuser());
                    ps.executeUpdate();
                    ps.close();

                    String select = "select points from user where username='" + usu2.getNombreuser() + "'";
                    Statement st = conexion.createStatement();
                    ResultSet rs = st.executeQuery(select);
                    if (rs.next()) {
                        puntos = (rs.getInt("points"));
                    }
                    rs.close();
                    st.close();

                    int puntosTotal = puntos + points;

                    if (puntosTotal > 15) {
                        String update3 = "update user set level=(level+1), points=0 where username=?";
                        PreparedStatement ps3 = conexion.prepareStatement(update3);
                        ps3.setString(1, usu2.getNombreuser());
                        ps3.executeUpdate();
                        ps3.close();
                    } else {
                        String update4 = "update user set points=? where username=?";
                        PreparedStatement ps4 = conexion.prepareStatement(update4);
                        ps4.setInt(1, puntosTotal);
                        ps4.setString(2, usu2.getNombreuser());
                        ps4.executeUpdate();
                        ps4.close();
                    }
                    System.out.println("El ganador es " + usu2.getNombreuser());
                }
            }
        } //****************OPCION 2***************************
        if (pokedex2.getVidaActual() > pokedex1.getVidaActual()) {
            while (pokedex2.getVidaActual() > 0 && pokedex1.getVidaActual() > 0) {
                int fuerzaAtaque = ((pokedex2.getPc() / 2) + ((int) (Math.floor(Math.random() * (4 - 1 + 1) + 1))));
                pokedex1.setVidaActual(pokedex1.getVidaActual() - fuerzaAtaque);
                if (pokedex1.getVidaActual() > 0) {
                    fuerzaAtaque = ((pokedex1.getPc() / 2) + ((int) (Math.floor(Math.random() * (4 - 1 + 1) + 1))));
                    pokedex2.setVidaActual(pokedex2.getVidaActual() - fuerzaAtaque);
                } else {
                    insertarLucha(pokedex1, pokedex2, pokedex2);
                    pokedex1.setVidaActual(0);

                    String actulizarVida = "update pokedex set lifecurrent=? where idpokedex=?";
                    PreparedStatement ac = conexion.prepareStatement(actulizarVida);
                    ac.setInt(1, pokedex2.getVidaActual());
                    ac.setInt(2, pokedex2.getId());
                    ac.executeUpdate();
                    ac.close();

                    String actulizarVida2 = "update pokedex set lifecurrent=0 where idpokedex=?";
                    PreparedStatement ac2 = conexion.prepareStatement(actulizarVida2);
                    ac2.setInt(1, pokedex1.getId());
                    ac2.executeUpdate();
                    ac2.close();

                    String update = "update user set pokecoins=(pokecoins+?) where username=?";
                    PreparedStatement ps = conexion.prepareStatement(update);
                    ps.setInt(1, stucoins);
                    ps.setString(2, usu2.getNombreuser());
                    ps.executeUpdate();
                    ps.close();

                    String select = "select points from user where username='" + usu2.getNombreuser() + "'";
                    Statement st = conexion.createStatement();
                    ResultSet rs = st.executeQuery(select);
                    if (rs.next()) {
                        puntos = (rs.getInt("points"));
                    }
                    rs.close();
                    st.close();

                    int puntosTotal = puntos + points;

                    if (puntosTotal > 15) {
                        String update3 = "update user set level=(level+1), points=0 where username=?";
                        PreparedStatement ps3 = conexion.prepareStatement(update3);
                        ps3.setString(1, usu2.getNombreuser());
                        ps3.executeUpdate();
                        ps3.close();
                    } else {
                        String update4 = "update user set points=? where username=?";
                        PreparedStatement ps4 = conexion.prepareStatement(update4);
                        ps4.setInt(1, puntosTotal);
                        ps4.setString(2, usu2.getNombreuser());
                        ps4.executeUpdate();
                        ps4.close();
                    }
                    System.out.println("El ganador es " + usu2.getNombreuser());
                }
                if (pokedex2.getVidaActual() <= 0) {
                    insertarLucha(pokedex1, pokedex2, pokedex1);
                    pokedex2.setVidaActual(0);

                    String actulizarVida = "update pokedex set lifecurrent=? where idpokedex=?";
                    PreparedStatement ac = conexion.prepareStatement(actulizarVida);
                    ac.setInt(1, pokedex1.getVidaActual());
                    ac.setInt(2, pokedex1.getId());
                    ac.executeUpdate();
                    ac.close();

                    String actulizarVida2 = "update pokedex set lifecurrent=0 where idpokedex=?";
                    PreparedStatement ac2 = conexion.prepareStatement(actulizarVida2);
                    ac2.setInt(1, pokedex2.getId());
                    ac2.executeUpdate();
                    ac2.close();

                    String update = "update user set pokecoins=(pokecoins+?) where username=?";
                    PreparedStatement ps = conexion.prepareStatement(update);
                    ps.setInt(1, stucoins);
                    ps.setString(2, usu1.getNombreuser());
                    ps.executeUpdate();
                    ps.close();

                    String select = "select points from user where username='" + usu1.getNombreuser() + "'";
                    Statement st = conexion.createStatement();
                    ResultSet rs = st.executeQuery(select);
                    if (rs.next()) {
                        puntos = (rs.getInt("points"));
                    }
                    rs.close();
                    st.close();

                    int puntosTotal = puntos + points;

                    if (puntosTotal > 15) {
                        String update3 = "update user set level=(level+1), points=0 where username=?";
                        PreparedStatement ps3 = conexion.prepareStatement(update3);
                        ps3.setString(1, usu1.getNombreuser());
                        ps3.executeUpdate();
                        ps3.close();
                    } else {
                        String update4 = "update user set points=? where username=?";
                        PreparedStatement ps4 = conexion.prepareStatement(update4);
                        ps4.setInt(1, puntosTotal);
                        ps4.setString(2, usu1.getNombreuser());
                        ps4.executeUpdate();
                        ps4.close();
                    }
                    System.out.println("El ganador es " + usu1.getNombreuser());
                }
            }
        }
    }
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
        // Creamos pokemon para comprobar si existe
        Pokemon pokemon = new Pokemon(nombre);

        if (!existePokemon(pokemon)) {
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
                    System.out.println("·Contraseña: " + pass);
                    System.out.println("·Contraseña Base Datos: " + passdes);
                }
            } catch (Exception ex) {
                throw new Excepcion("ERROR: Contraseña no coinciden");
            }
        }
        return contraseña;
    }

    public List<Pokemon> getPokemonByUserPlace(Usuario usu) throws SQLException, Excepcion {
        // Creamos usuario para comprobar si existe
        List<Pokemon> pokemon = new ArrayList<>();
        if (!existeUsuario(usu)) {
            throw new Excepcion("ERROR: No existe ningún usuario con ese nombre");
        }
        String select = "select pokemon.name, pokemon.type, pokemon.pc, pokemon.life, pokemon.place, "
                + "user.username, user.place  from pokemon inner join user on pokemon.place = user.place "
                + "where pokemon.place = (select place from user where username = '" + usu.getNombreuser() + "')";
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
            System.out.println("·Datos del Usuario:");
            System.out.println("·Nombre: " + nombreUser);
            System.out.println("·Lugar: " + lugarUser);
        } else {
            throw new Excepcion("ERROR: No hay ningun pokemon en el lugar del usuario");
        }
        rs.close();
        st.close();
        return pokemon;
    }

    public List<Usuario> getUsersByUserPlace(Usuario usu) throws SQLException, Excepcion {
        // Creamos usuario para comprobar si existe
        List<Usuario> usuarios = new ArrayList<>();
        if (!existeUsuario(usu)) {
            throw new Excepcion("ERROR: No existe ningún usuario con ese nombre");
        }
        String select = "select * from user where place='" + usu.getLugar() + "'";
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery(select);
        String nombreUser = "";
        String lugarUser = "";
        if (rs.first()) {//recorre el resultset al siguiente registro si es que existen
            rs.beforeFirst();//regresa el puntero al primer registro
            while (rs.next()) {
                Usuario user = new Usuario();
                //Para que no aparezaca el propio usuario en la lista.
                if (rs.getString("username").equals(usu.getNombreuser())) {
                    nombreUser = usu.getNombreuser();
                    lugarUser = usu.getLugar();
                } else {
                    user.setNombreuser(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    user.setPokeballs(rs.getInt("pokeballs"));
                    user.setPociones(rs.getInt("potions"));
                    user.setNivel(rs.getInt("level"));
                    user.setLugar(rs.getString("place"));
                    user.setMonedas(rs.getInt("pokecoins"));
                    user.setPuntos(rs.getInt("points"));
                    usuarios.add(user);
                }
            }
            System.out.println("·Datos del Usuario:");
            System.out.println("·Nombre: " + nombreUser);
            System.out.println("·Lugar: " + lugarUser);
        } else {
            throw new Excepcion("ERROR: No hay ningun pokemon en el lugar del usuario");
        }
        rs.close();
        st.close();
        return usuarios;
    }

    public List<Pokeparada> getPokeparadaByUserPlace(Usuario usu) throws SQLException, Excepcion {
        // Creamos usuario para comprobar si existe
        List<Pokeparada> pokeparadas = new ArrayList<>();
        if (!existeUsuario(usu)) {
            throw new Excepcion("ERROR: No existe ningún usuario con ese nombre");
        }
        String select = "select * from pokeparada where place='" + usu.getLugar() + "'";
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery(select);
        String nombreUser = usu.getNombreuser();
        String lugarUser = usu.getLugar();
        if (rs.first()) {//recorre el resultset al siguiente registro si es que existen
            rs.beforeFirst();//regresa el puntero al primer registro
            while (rs.next()) {
                Pokeparada pokeparada = new Pokeparada();
                pokeparada.setNombre(rs.getString("name"));
                pokeparada.setLugar(rs.getString("place"));
                pokeparada.setPokeballs(rs.getInt("pokeballs"));
                pokeparada.setPociones(rs.getInt("potions"));
                pokeparadas.add(pokeparada);

            }
            System.out.println("·Datos del Usuario:");
            System.out.println("·Nombre: " + nombreUser);
            System.out.println("·Lugar: " + lugarUser);
        } else {
            throw new Excepcion("ERROR: No hay ningun pokemon en el lugar del usuario");
        }
        rs.close();
        st.close();
        return pokeparadas;
    }

    public List<Pokedex> getPokedexByUser(Usuario usu) throws SQLException, Excepcion {
        // Creamos usuario para comprobar si existe
        List<Pokedex> pokedex = new ArrayList<>();
        if (!existeUsuario(usu)) {
            throw new Excepcion("ERROR: No existe ningún usuario con ese nombre");
        }
        String select = "select * from pokedex where user='" + usu.getNombreuser() + "'";
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery(select);
        String nombreUser = usu.getNombreuser();
        if (rs.first()) {//recorre el resultset al siguiente registro si es que existen
            rs.beforeFirst();//regresa el puntero al primer registro
            while (rs.next()) {
                Pokedex poke = new Pokedex();
                poke.setId(rs.getInt("idpokedex"));
                Pokemon pokemon = new Pokemon(rs.getString("pokemon"));
                poke.setPokemon(pokemon);
                poke.setFecha(rs.getDate("date").toLocalDate());
                poke.setPc(rs.getInt("pc"));
                poke.setMaximaVida(rs.getInt("lifemax"));
                poke.setVidaActual(rs.getInt("lifecurrent"));
                pokedex.add(poke);
                System.out.println("Id: " + poke.getId() + " - NombrePokemon: " + poke.getPokemon().getNombre()
                        + " - FechaCaptura: " + poke.getFecha() + " - Pc: " + poke.getPc()
                        + " - VidaActual: " + poke.getVidaActual() + " - VidaMaxima: " + poke.getMaximaVida());
            }
            System.out.println("·Datos del Usuario:");
            System.out.println("·Nombre: " + nombreUser);
        } else {
            throw new Excepcion("ERROR: No hay ningun pokemon en el lugar del usuario");
        }
        rs.close();
        st.close();
        return pokedex;
    }

    public List<RankingUsuarios> getRankingUsuarios() throws SQLException {
        List<RankingUsuarios> rankingUsuario = new ArrayList<>();

        String select = "select user, count(pokemon) as numPoke from pokedex group by user order by count(pokemon) desc";
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery(select);
        int ranking = 1;
        while (rs.next()) {
            RankingUsuarios rankingUsu = new RankingUsuarios();
            rankingUsu.setRanking(ranking);
            rankingUsu.setNombreUser(rs.getString("user"));
            rankingUsu.setNumPokemon(rs.getInt("numPoke"));
            rankingUsuario.add(rankingUsu);
            ranking++;
        }
        st.close();
        rs.close();
        return rankingUsuario;
    }

    public List<RankingPokemon> getRankingPokemon() throws SQLException {
        List<RankingPokemon> rankingPokemon = new ArrayList<>();

        String select = "select pokedex.pokemon, pokedex.pc, pokedex.lifecurrent, pokedex.user from pokedex inner join fight "
                + "on pokedex.idpokedex = fight.winner group by winner order by count(winner) desc limit 10;";
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery(select);
        int ranking = 1;
        while (rs.next()) {
            //Acabar consulta
            RankingPokemon rankingPoke = new RankingPokemon();
            rankingPoke.setRanking(ranking);
            rankingPoke.setNombre(rs.getString("pokedex.pokemon"));
            rankingPoke.setPc(rs.getInt("pokedex.pc"));
            rankingPoke.setVida(rs.getInt("pokedex.lifecurrent"));
            rankingPoke.setNombreUser(rs.getString("pokedex.user"));
            rankingPokemon.add(rankingPoke);
            ranking++;
        }
        st.close();
        rs.close();
        return rankingPokemon;
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

    // Función que da de alta una pokedex
    public void insertarPokedex(Pokedex pokedex, Usuario usu) throws SQLException, Excepcion {

        String insert = "insert into pokedex values (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = conexion.prepareStatement(insert);
        ps.setInt(1, pokedex.getId());
        ps.setString(2, usu.getNombreuser());
        ps.setString(3, pokedex.getPokemon().getNombre());

        //Convertimos el formato LocalDate to Date
        Date date = java.sql.Date.valueOf(pokedex.getFecha());
        ps.setDate(4, date);
        ps.setInt(5, pokedex.getPc());
        ps.setInt(6, pokedex.getMaximaVida());
        ps.setInt(7, pokedex.getVidaActual());
        ps.executeUpdate();
        ps.close();
    }

    public void insertarLucha(Pokedex pokedex, Pokedex pokedex2, Pokedex winner) throws SQLException, Excepcion {

        String insert = "insert into fight values (?, ?, ?, ?)";
        PreparedStatement ps = conexion.prepareStatement(insert);
        ps.setInt(1, 0);
        ps.setInt(2, pokedex.getId());
        ps.setInt(3, pokedex2.getId());
        ps.setInt(4, winner.getId());
        ps.executeUpdate();
        ps.close();
    }

    // ********************* Updates ******************************************
    // Función que modifica el lugar de un usuario
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
        System.out.println("·Lugar usuario modificado");

    }

    // Función que modifica el lugar de un pokemon
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
        System.out.println("·Lugar pokemon modificado");
    }

    // Función que modifica las pokeballs de un usuario
    public void modificarPokeballsCaptura(Usuario usu) throws SQLException, Excepcion {
        String update = "update user set pokeballs=(pokeballs-1) where username=?";
        PreparedStatement ps = conexion.prepareStatement(update);
        ps.setString(1, usu.getNombreuser());
        ps.executeUpdate();
        ps.close();
        System.out.println("·Pokeballs modificadas");
    }

    public void añadirCoinsUser(Usuario usu, Integer numero) throws SQLException, Excepcion {
        String update = "update user set pokecoins=(pokecoins +" + numero + ") where username=?";
        PreparedStatement ps = conexion.prepareStatement(update);
        ps.setString(1, usu.getNombreuser());
        ps.executeUpdate();
        ps.close();
    }

    public void mejorarPokedexUser(Usuario usu, Pokedex pokedex) throws SQLException, Excepcion {
        if (!existeUsuario(usu)) {
            throw new Excepcion("ERROR: No existe ningún usuario con ese nombre");
        }
        if (!existePokedexByUser(pokedex, usu)) {
            throw new Excepcion("ERROR: El usuario no tiene el pokemon en su pokedex");
        }
        if (!coinsSuficientes(usu)) {
            throw new Excepcion("ERROR: El usuario no tinene stucoins suficientes para mejorar al pokemon");
        } else {
            if (pokedex.getMaximaVida() == pokedex.getVidaActual()) {
                //Lo mejoramos en la base de datos
                String update = "update pokedex set lifemax=(lifemax+" + 2 + "), lifecurrent=(lifecurrent+" + 2 + "), "
                        + "pc=(pc+" + 5 + ") " + "where idpokedex=?";
                PreparedStatement ps = conexion.prepareStatement(update);
                ps.setInt(1, pokedex.getId());
                ps.executeUpdate();
                ps.close();
                System.out.println("Vida y Pc mejorada");
            } else {
                //Lo mejoramos en la base de datos
                String update = "update pokedex set lifemax=(lifemax+" + 2 + "), pc=(pc+" + 5 + ") "
                        + "where idpokedex=?";
                PreparedStatement ps = conexion.prepareStatement(update);
                ps.setInt(1, pokedex.getId());
                ps.executeUpdate();
                ps.close();
                System.out.println("·Vida y Pc mejorada");
            }
        }
    }

    public void curarPokemonUser(Usuario usu, Pokedex pokedex) throws SQLException, Excepcion {
        if (!existeUsuario(usu)) {
            throw new Excepcion("ERROR: No existe ningún usuario con ese nombre");
        }
        if (!pocionesUsuario(usu)) {
            throw new Excepcion("ERROR: El usuario no tiene pociones");
        }
        if (!existePokedexByUser(pokedex, usu)) {
            throw new Excepcion("ERROR: El usuario no tiene el pokemon en su pokedex");
        }
        if (pokedex.getVidaActual() == pokedex.getMaximaVida()) {
            throw new Excepcion("ERROR: El pokemon no ha sufrido daño");
        } else {
            int contador = 0;
            int pociones = 1;
            while (pociones > 0 && pokedex.getVidaActual() < pokedex.getMaximaVida()) {
                pokedex.setVidaActual(pokedex.getVidaActual() + 30);

                if (pokedex.getVidaActual() > pokedex.getMaximaVida()) {
                    pokedex.setVidaActual(pokedex.getMaximaVida());
                }
                //Actualizamos las pociones del usuarios.
                String update2 = "update user set potions=(potions-1) where username=?";
                PreparedStatement ps2 = conexion.prepareStatement(update2);
                ps2.setString(1, usu.getNombreuser());
                ps2.executeUpdate();
                ps2.close();
                contador++;
                //Y consultamos las pociones que le quedan.
                String select = "select potions from user where username='" + usu.getNombreuser() + "'";
                Statement st = conexion.createStatement();
                ResultSet rs = st.executeQuery(select);
                if (rs.next()) {
                    pociones = (rs.getInt("potions"));
                }
                rs.close();
                st.close();
            }
            String update = "update pokedex set lifecurrent=? where idpokedex=?";
            PreparedStatement ps = conexion.prepareStatement(update);
            ps.setInt(1, pokedex.getVidaActual());
            ps.setInt(2, pokedex.getId());
            ps.executeUpdate();
            ps.close();
            System.out.println("El usuario ha gastado, " + contador + " pociones");
        }
    }

    // ********************* Deletes ******************************************
    //Elimina pokemon de la pokedex de un usuario
    public void liberarPokemonPokedex(Usuario usu, Pokedex pokedex) throws SQLException, Excepcion {
        if (!existeUsuario(usu)) {
            throw new Excepcion("ERROR: No existe ningún usuario con ese nombre");
        }
        if (!existePokedexByUser(pokedex, usu)) {
            throw new Excepcion("ERROR: El usuario no tiene el pokemon en su pokedex");
        }
        String delete = "DELETE FROM pokedex WHERE idpokedex=?";
        PreparedStatement ps = conexion.prepareStatement(delete);
        ps.setInt(1, pokedex.getId());
        ps.executeUpdate();
        ps.close();
        System.out.println("·Pokemon eliminado de la pokedex del usuario");
        añadirCoinsUser(usu, 25);
        System.out.println("25 Pokecoins añadidas");
    }

    // ********************* Funciones adicionales ****************************
    private boolean calcularPokeballs(Usuario usu) throws SQLException {
        String select = "select pokeballs from user where username= '" + usu.getNombreuser() + "'";
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery(select);
        boolean tienePokeballs = false;

        if (rs.next()) {
            if (1 <= rs.getInt("pokeballs")) {
                tienePokeballs = true;
            }
        }
        rs.close();
        st.close();
        return tienePokeballs;
    }

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

    private boolean existePokedexByUser(Pokedex pokedex, Usuario usu) throws SQLException {
        String select = "select * from pokedex where idpokedex='" + pokedex.getId() + "'"
                + "and user='" + usu.getNombreuser() + "'";
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

    private boolean coinsSuficientes(Usuario usu) throws SQLException {
        String select = "select pokecoins from user where username='" + usu.getNombreuser() + "'";
        Statement st = conexion.createStatement();
        boolean coins = false;
        ResultSet rs = st.executeQuery(select);
        if (rs.next()) {
            if (rs.getInt("pokecoins") > 100) {
                coins = true;
            }
        }
        rs.close();
        st.close();
        return coins;
    }

    private boolean pocionesUsuario(Usuario usu) throws SQLException {
        String select = "select potions from user where username='" + usu.getNombreuser() + "'";
        Statement st = conexion.createStatement();
        boolean potions = false;
        ResultSet rs = st.executeQuery(select);
        if (rs.next()) {
            if (rs.getInt("potions") > 0) {
                potions = true;
            }
        }
        rs.close();
        st.close();
        return potions;
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
