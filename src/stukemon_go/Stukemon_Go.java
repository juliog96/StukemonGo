/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stukemon_go;

import dao.StukemonDAO;
import excepciones.Excepcion;
import java.sql.SQLException;
import java.util.List;
import modelo.Pokemon;
import modelo.Pokeparada;
import modelo.Usuario;

/**
 *
 * @author julio
 */
public class Stukemon_Go {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        StukemonDAO stukemonDAO = new StukemonDAO();

        //Se regalan 20 pokeballs que se definen en la clase usuario 
        Usuario user1 = new Usuario("Lucas", "Ro34", "Mosso");
        Pokemon pk1 = new Pokemon("Pikachu", "Electrico", 0, 0, "C/Pelai");
        Pokemon pk2 = new Pokemon("Charizar", "Fuego", 0, 0, "Mosso");
        Pokeparada pokepa1 = new Pokeparada("Losa2", "Sagrada Familia", 2, 4);

        // Conectamos a la base de datos
        try {
            System.out.println("************************************************************");
            System.out.println("-- Testeando conexión con la base de datos --");
            stukemonDAO.conectar();
            System.out.println("Establecida la conexión.");
        } catch (SQLException ex) {
            System.out.println("Error al conectar / desconectar: " + ex.getMessage());
        }
        //Registramos usuario
        try {
            System.out.println("************************************************************");
            System.out.println("-- Testeando insert user " + user1.getNombreuser() + " --");
            altaUser(stukemonDAO, user1);
        } catch (SQLException ex) {
            System.out.println("Error al insertar pokemon: " + ex.getMessage());
        }
        //Buscamos usuario por nombreuser
        try {
            System.out.println("************************************************************");
            System.out.println("-- Testeando obtener usuario por el username: Julio --");
            obtenerUsuario(stukemonDAO, "Julio");
        } catch (SQLException ex) {
            System.out.println("Error al obtener usuario: " + ex.getMessage());
        }
        //Registramos pokemon
        try {
            System.out.println("************************************************************");
            System.out.println("-- Testeando insert pokemon " + pk1.getNombre() + " --");
            altaPokemon(stukemonDAO, pk1);
        } catch (SQLException ex) {
            System.out.println("Error al insertar pokemon: " + ex.getMessage());
        }
        //Registramos pokeparada
        try {
            System.out.println("************************************************************");
            System.out.println("-- Testeando insert pokeparada " + pokepa1.getNombre() + " --");
            altaPokeparada(stukemonDAO, pokepa1);
        } catch (SQLException ex) {
            System.out.println("Error al insertar pokeparada: " + ex.getMessage());
        }
        //Validamos login usuario
        try {
            System.out.println("************************************************************");
            System.out.println("-- Testeando login usuario: Pepe --");
            validarUser(stukemonDAO, "Pepe", "Rot34");
        } catch (SQLException ex) {
            System.out.println("Error al logear usuario: " + ex.getMessage());
        }
        //Modificamos lugar usuario
        try {
            System.out.println("************************************************************");
            System.out.println("-- Testeando modificar lugar del usuario: Pepe --");
            System.out.println("Datos Actuales:");
            System.out.println(user1);
            System.out.println("Estableciendo lugar en Splau");
            user1.setLugar("Splau");
            modificarLugarUser(stukemonDAO, user1);
        } catch (SQLException ex) {
            System.out.println("Error al modificar usuario: " + ex.getMessage());
        }
        //Modificamos lugar pokemon
        try {
            System.out.println("************************************************************");
            System.out.println("-- Testeando modificar lugar del pokemon: Pikachu --");
            System.out.println("Datos Actuales:");
            System.out.println(pk1);
            System.out.println("Estableciendo lugar en Gran Via 2");
            pk1.setLugar("Gran Via 2");
            modificarLugarPokemon(stukemonDAO, pk1);
        } catch (SQLException ex) {
            System.out.println("Error al modificar usuario: " + ex.getMessage());
        }
        //Listado pokemon que estan en el mismo lugar que un usuario dado
        try {
            System.out.println("************************************************************");
            System.out.println("-- Testeando obtener listado pokemon que estan en el mismo lugar que el usuario: Julio --");
            obtenerListaPokemonByUserPlace(stukemonDAO, user1);
        } catch (SQLException ex) {
            System.out.println("Error al obtener listado pokemon: " + ex.getMessage());
        }
        //Capturar pokemon recibinedo usuario y pokemon
        try {
            System.out.println("************************************************************");
            System.out.println("-- Testeando capturar pokemon --");
            capturarPokemon(stukemonDAO, user1, pk2);
        } catch (SQLException ex) {
            System.out.println("Error al obtener listado pokemon: " + ex.getMessage());
        }
        //Liberar pokemon recibinedo usuario y pokemon
        //Listado usuarios que estan en el mismo lugar que un usuario dado
        //Listado pokeparadas que estan en el mismo lugar que un usuario dado
        //Coger reaglos pokeparadas recibiendo usuario y pokeparada

    }

    private static void altaUser(StukemonDAO stukemonDAO, Usuario usu) throws SQLException {
        try {
            stukemonDAO.insertarUsuario(usu);
            System.out.println("Usuario con nombre, " + usu.getNombreuser() + " dado de alta");
        } catch (Excepcion ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void obtenerUsuario(StukemonDAO stukemonDAO, String nombreuser) throws SQLException {
        try {
            Usuario usu = stukemonDAO.getUsuarioByNombre(nombreuser);
            System.out.println("Datos del Usuario");
            System.out.println(usu);
        } catch (Excepcion ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void altaPokemon(StukemonDAO stukemonDAO, Pokemon pk) throws SQLException {
        try {
            stukemonDAO.insertarPokemon(pk);
            System.out.println("Pokemen con nombre, " + pk.getNombre() + " dado de alta");
        } catch (Excepcion ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void altaPokeparada(StukemonDAO stukemonDAO, Pokeparada pokepa) throws SQLException {
        try {
            stukemonDAO.insertarPokeparada(pokepa);
            System.out.println("Pokeparada con nombre, " + pokepa.getNombre() + " dado de alta");
        } catch (Excepcion ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void validarUser(StukemonDAO stukemonDAO, String nombreuser, String pass) throws SQLException {
        try {
            boolean validacion = stukemonDAO.validarPassword(nombreuser, pass);
            if (validacion == true) {
                System.out.println("Usuario logeado correctamente");
            } else {
                throw new Excepcion("ERROR: Contraseña no coinciden");
            }
        } catch (Excepcion ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void modificarLugarUser(StukemonDAO stukemonDAO, Usuario user) throws SQLException {
        try {
            stukemonDAO.modificarLugarUsuario(user);
            System.out.println("Lugar modificado.");
            System.out.println("Obteniendo datos de la BBDD del usuario " + user.getNombreuser() + " para comprobar el nuevo lugar");
            Usuario aux = stukemonDAO.getUsuarioByNombre(user.getNombreuser());
            System.out.println("Nuevos datos:");
            System.out.println(aux);
        } catch (Excepcion ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void modificarLugarPokemon(StukemonDAO stukemonDAO, Pokemon pk) throws SQLException {
        try {
            stukemonDAO.modificarLugarPokemon(pk);
            System.out.println("Lugar modificado.");
            System.out.println("Obteniendo datos de la BBDD del pokemon " + pk.getNombre() + " para comprobar el nuevo lugar");
            Pokemon aux = stukemonDAO.getPokemonByNombre(pk);
            System.out.println("Nuevos datos:");
            System.out.println(aux);
        } catch (Excepcion ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void obtenerListaPokemonByUserPlace(StukemonDAO stukemonDAO, Usuario user) throws SQLException {
        try {
            System.out.println("Datos del usuario:");
            List<Pokemon> pk = stukemonDAO.getPokemonByUserPlace(user);
            System.out.println("Datos de los pokemon:");
            for (Pokemon poke : pk) {
                System.out.println(poke);
            }
        } catch (Excepcion ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void capturarPokemon(StukemonDAO stukemonDAO, Usuario user, Pokemon pokemon) throws SQLException {
        try {
            stukemonDAO.capturarPokemon(user, pokemon);
            System.out.println("El pokemon, "+pokemon.getNombre()+" ha sido capturado por el usuario "+user.getNombreuser());
        } catch (Excepcion ex) {
            System.out.println(ex.getMessage());
        }
    }
}
