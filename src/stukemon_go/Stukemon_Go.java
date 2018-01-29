/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stukemon_go;

import dao.StukemonDAO;
import excepciones.Excepcion;
import java.sql.SQLException;
import java.time.LocalDate;
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
public class Stukemon_Go {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Excepcion {

        StukemonDAO stukemonDAO = new StukemonDAO();

        //Se regalan 20 pokeballs que se definen en la clase usuario 
        Usuario user1 = new Usuario("Lucas", "Ro34", "Mosso");
        Usuario user2 = new Usuario("Julio", "Lol21", "Sagrada Familia");
        Usuario user3 = new Usuario("Luis", "1234", "Anec Blau");
        Usuario user4 = new Usuario("Antonia", "12345", "Anec Blau");

        Pokemon pk1 = new Pokemon("Pikachu", "Electrico", 15, 30, "C/Pelai");
        Pokemon pk2 = new Pokemon("Charizar", "Fuego", 20, 50, "Splau");
        Pokemon pk3 = new Pokemon("Bulbasur", "Planta", 20, 100, "Splau");
        Pokemon pk4 = new Pokemon("Ivysaur", "Agua", 20, 50, "Barnasud");
        Pokemon pk5 = new Pokemon("Arbok", "Veneno", 20, 60, "Stucom");

        Pokeparada pokepa1 = new Pokeparada("Losa2", "Sagrada Familia", 2, 4);
        Pokedex pokedex1 = new Pokedex(pk1, LocalDate.now(), pk1.getPc(), 20, pk1.getVida());
        Pokedex pokedex2 = new Pokedex(pk2, LocalDate.now(), pk2.getPc(), pk2.getVida(), pk2.getVida());
        Pokedex pokedex3 = new Pokedex(pk3, LocalDate.now(), pk3.getPc(), pk3.getVida(), 20);
        Pokedex pokedex4 = new Pokedex(pk4, LocalDate.now(), pk4.getPc(), pk4.getVida(), pk4.getVida());
        Pokedex pokedex5 = new Pokedex(pk5, LocalDate.now(), pk5.getPc(), pk5.getVida(), pk5.getVida());

        // Conectamos a la base de datos
        try {
            System.out.println("************************************************************");
            System.out.println("-- Testeando conexión con la base de datos --");
            stukemonDAO.conectar();
            System.out.println("·Establecida la conexión.");

            //Registramos usuario
            System.out.println("************************************************************");
            System.out.println("-- Testeando insert user " + user2.getNombreuser() + " --");
            altaUser(stukemonDAO, user2);

            //Registramos usuario
            System.out.println("************************************************************");
            System.out.println("-- Testeando insert user " + user2.getNombreuser() + " --");
            altaUser(stukemonDAO, user3);

            //Registramos usuario
            System.out.println("************************************************************");
            System.out.println("-- Testeando insert user " + user2.getNombreuser() + " --");
            altaUser(stukemonDAO, user4);

            //Buscamos usuario por nombreuser
            System.out.println("************************************************************");
            System.out.println("-- Testeando obtener usuario por el username: Julio --");
            obtenerUsuario(stukemonDAO, "Julio");

            //Registramos pokemon
            System.out.println("************************************************************");
            System.out.println("-- Testeando insert pokemon " + pk1.getNombre() + " --");
            altaPokemon(stukemonDAO, pk1);

            //Registramos pokemon
            System.out.println("************************************************************");
            System.out.println("-- Testeando insert pokemon " + pk4.getNombre() + " --");
            altaPokemon(stukemonDAO, pk4);

            //Registramos pokemon
            System.out.println("************************************************************");
            System.out.println("-- Testeando insert pokemon " + pk5.getNombre() + " --");
            altaPokemon(stukemonDAO, pk5);

            //Registramos pokeparada
            System.out.println("************************************************************");
            System.out.println("-- Testeando insert pokeparada " + pokepa1.getNombre() + " --");
            altaPokeparada(stukemonDAO, pokepa1);

            //Validamos login usuario
            System.out.println("************************************************************");
            System.out.println("-- Testeando login usuario: Pepe --");
            validarUser(stukemonDAO, "Pepe", "Rot34");

            //Modificamos lugar usuario
            System.out.println("************************************************************");
            System.out.println("-- Testeando modificar lugar del usuario: Pepe --");
            System.out.println("·Datos Actuales:");
            System.out.println(user1);
            System.out.println("·Estableciendo lugar en Splau");
            user1.setLugar("Splau");
            modificarLugarUser(stukemonDAO, user1);

            //Modificamos lugar pokemon
            System.out.println("************************************************************");
            System.out.println("-- Testeando modificar lugar del pokemon: Pikachu --");
            System.out.println("·Datos Actuales:");
            System.out.println(pk1);
            System.out.println("·Estableciendo lugar en Gran Via 2");
            pk1.setLugar("Gran Via 2");
            modificarLugarPokemon(stukemonDAO, pk1);

            //Listado pokemon que estan en el mismo lugar que un usuario dado
            System.out.println("************************************************************");
            System.out.println("-- Testeando obtener listado pokemon que estan en el mismo lugar que el usuario: Julio --");
            obtenerListaPokemonByUserPlace(stukemonDAO, user1);

            //Capturar pokemon recibinedo usuario y pokemon
            System.out.println("************************************************************");
            System.out.println("-- Testeando capturar pokemon, Usuario: " + user1.getNombreuser() + " / Pokemon: " + pk2.getNombre() + " --");
            capturarPokemon(stukemonDAO, user1, pk2);

            //Reptimos el metodo para comprobar que el pokemon ha cambiado de lugar
            System.out.println("-- Testeando si cambia de lugar tanto si se captura como si escapa el pokemon --");
            capturarPokemon(stukemonDAO, user1, pk2);

            //Liberar pokemon recibinedo usuario y pokemon
            System.out.println("************************************************************");
            //Le creo el id random porque sino cuando entra en el array de pokemon entra como 0
            //y cuando hacemos la consulta a la base de datos es otro id porque en la base de datos es
            //autoincrementable y para buscar una pokedex por id seria imposible.
            int valorEntero = (int) Math.floor(Math.random() * (9000 - 1000 + 1) + 1000);
            pokedex2.setId(valorEntero);
            user1.getPokedex().add(pokedex2);
            stukemonDAO.insertarPokedex(pokedex2, user1);
            System.out.println("-- Testeando liberar pokemon: Lucas --");
            liberarPokemon(stukemonDAO, user1, user1.getPokedex().get(0));

            //Listado usuarios que estan en el mismo lugar que un usuario dado
            System.out.println("************************************************************");
            System.out.println("-- Testeando obtener listado usuarios que estan en el mismo lugar que el usuario: Lucas --");
            obtenerListaUsersByUserPlace(stukemonDAO, user1);

            //Listado pokeparadas que estan en el mismo lugar que un usuario dado
            System.out.println("************************************************************");
            System.out.println("-- Testeando obtener listado pokeparadas que estan en el mismo lugar que el usuario: Lucas --");
            obtenerListaPokeparadaByUserPlace(stukemonDAO, user1);

            //Coger regalos pokeparadas recibiendo usuario y pokeparada
            System.out.println("************************************************************");
            System.out.println("-- Testeando obtener regalos de la pokeparada: Osa12 / usuario: Julio  --");
            System.out.println("·Datos Actuales:");
            System.out.println(user2);
            System.out.println("·Recoginedo los regalos de la pokeparada");
            obtenerRegalos(stukemonDAO, user2, pokepa1);

            //Combate entre dos pokemon de dos usuarios
            System.out.println("************************************************************");
            //Le creo el id random porque sino cuando entra en el array de pokemon entra como 0
            //y cuando hacemos la consulta a la base de datos es otro porque en la base de datos es
            //autoincrementable y para buscar una pokedex por id seria imposible.            
            //Inserto una pokedex al usuario3 para probar el combate
            valorEntero = (int) Math.floor(Math.random() * (9000 - 1000 + 1) + 1000);
            pokedex4.setId(valorEntero);
            user3.getPokedex().add(pokedex4);
            stukemonDAO.insertarPokedex(pokedex4, user3);
            //Inserto una pokedex al usuario4 para probar el combate
            valorEntero = (int) Math.floor(Math.random() * (9000 - 1000 + 1) + 1000);
            pokedex5.setId(valorEntero);
            user4.getPokedex().add(pokedex5);
            stukemonDAO.insertarPokedex(pokedex5, user4);
            System.out.println("-- Testeando obtener combate entre dos usuarios con dos pokemon determinados --");
            combate(stukemonDAO, user3, user3.getPokedex().get(0), user4, user4.getPokedex().get(0));

            //Curar a un pokemon de un usuario determinado
            System.out.println("************************************************************");
            //Le creo el id random porque sino cuando entra en el array de pokemon entra como 0
            //y cuando hacemos la consulta a la base de datos es otro porque en la base de datos es
            //autoincrementable y para buscar una pokedex por id seria imposible.
            valorEntero = (int) Math.floor(Math.random() * (9000 - 1000 + 1) + 1000);
            pokedex3.setId(valorEntero);
            user1.getPokedex().add(pokedex3);
            stukemonDAO.insertarPokedex(pokedex3, user1);
            System.out.println("-- Testeando curar pokemon del Usuario: Lucas / Pokemon: Charizar --");
            curarPokemonUser(stukemonDAO, user1, user1.getPokedex().get(1));

            //Mejorar vida y pc de un pokemon de un usuario determinado
            System.out.println("************************************************************");
            valorEntero = (int) Math.floor(Math.random() * (9000 - 1000 + 1) + 1000);
            pokedex1.setId(valorEntero);
            user1.getPokedex().add(pokedex1);
            stukemonDAO.insertarPokedex(pokedex1, user1);
            System.out.println("-- Testeando mejorar vida y pc del Usuario: Lucas / Pokemon: Charizar --");
            mejorarVidaPc(stukemonDAO, user1, user1.getPokedex().get(2));

            //Obtener la pokedex de un usuario
            System.out.println("************************************************************");
            System.out.println("-- Testeando obtener listado pokedex de el usuario: Lucas --");
            obtenerListaPokedexByUser(stukemonDAO, user1);

            //Obtener ranking de los usuarios que tengan mas pokemon
            System.out.println("************************************************************");
            System.out.println("-- Testeando obtener ranking usuarios con mas pokemon --");
            obtenerRankingUser(stukemonDAO);

            //Obtener ranking de los 10 mejores pokemon segun el numero de batallas ganadas
            System.out.println("************************************************************");
            System.out.println("-- Testeando obtener top 10 pokemon con mas batallas ganadas --");
            obtenerRankingPokemon(stukemonDAO);

        } catch (SQLException ex) {
            System.out.println("Error al conectar / desconectar: " + ex.getMessage());
        }
    }

    private static void altaUser(StukemonDAO stukemonDAO, Usuario usu) throws SQLException {
        try {
            stukemonDAO.insertarUsuario(usu);
            System.out.println("·Usuario con nombre, " + usu.getNombreuser() + " dado de alta");
        } catch (Excepcion ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void obtenerUsuario(StukemonDAO stukemonDAO, String nombreuser) throws SQLException {
        try {
            Usuario usu = stukemonDAO.getUsuarioByNombre(nombreuser);
            System.out.println("·Datos del Usuario");
            System.out.println(usu);
        } catch (Excepcion ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void altaPokemon(StukemonDAO stukemonDAO, Pokemon pk) throws SQLException {
        try {
            stukemonDAO.insertarPokemon(pk);
            System.out.println("·Pokemen con nombre, " + pk.getNombre() + " dado de alta");
        } catch (Excepcion ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void altaPokeparada(StukemonDAO stukemonDAO, Pokeparada pokepa) throws SQLException {
        try {
            stukemonDAO.insertarPokeparada(pokepa);
            System.out.println("·Pokeparada con nombre, " + pokepa.getNombre() + " dado de alta");
        } catch (Excepcion ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void validarUser(StukemonDAO stukemonDAO, String nombreuser, String pass) throws SQLException {
        try {
            boolean validacion = stukemonDAO.validarPassword(nombreuser, pass);
            if (validacion == true) {
                System.out.println("·Usuario logeado correctamente");
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
            System.out.println("·Obteniendo datos de la BBDD del usuario " + user.getNombreuser() + " para comprobar el nuevo lugar");
            Usuario aux = stukemonDAO.getUsuarioByNombre(user.getNombreuser());
            System.out.println("·Nuevos datos:");
            System.out.println(aux);
        } catch (Excepcion ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void modificarLugarPokemon(StukemonDAO stukemonDAO, Pokemon pk) throws SQLException {
        try {
            stukemonDAO.modificarLugarPokemon(pk);
            System.out.println("·Obteniendo datos de la BBDD del pokemon " + pk.getNombre() + " para comprobar el nuevo lugar");
            Pokemon aux = stukemonDAO.getPokemonByNombre(pk.getNombre());
            System.out.println("·Nuevos datos:");
            System.out.println(aux);
        } catch (Excepcion ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void obtenerListaPokemonByUserPlace(StukemonDAO stukemonDAO, Usuario user) throws SQLException {
        try {
            List<Pokemon> pk = stukemonDAO.getPokemonByUserPlace(user);
            System.out.println("·Datos de los pokemon:");
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
            System.out.println("·El pokemon, " + pokemon.getNombre() + " ha sido capturado por el usuario " + user.getNombreuser());
        } catch (Excepcion ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void liberarPokemon(StukemonDAO stukemonDAO, Usuario user, Pokedex pokedex) throws SQLException {
        try {
            stukemonDAO.liberarPokemonPokedex(user, pokedex);
        } catch (Excepcion ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void obtenerListaUsersByUserPlace(StukemonDAO stukemonDAO, Usuario user) throws SQLException {
        try {
            List<Usuario> usuarios = stukemonDAO.getUsersByUserPlace(user);
            System.out.println("·Datos de los usuarios:");
            for (Usuario usu : usuarios) {
                System.out.println(usu);
            }
        } catch (Excepcion ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void obtenerListaPokeparadaByUserPlace(StukemonDAO stukemonDAO, Usuario user) throws SQLException {
        try {
            List<Pokeparada> pokeparadas = stukemonDAO.getPokeparadaByUserPlace(user);
            System.out.println("·Datos de las pokeparadas:");
            for (Pokeparada poke : pokeparadas) {
                System.out.println(poke);
            }
        } catch (Excepcion ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void obtenerRegalos(StukemonDAO stukemonDAO, Usuario user, Pokeparada pokeparada) throws SQLException {
        try {
            stukemonDAO.cogerRegalos(user, pokeparada);
            System.out.println("·Obteniendo datos de la BBDD del usuario " + user.getNombreuser() + " para comprobar que los regalos se han añadido:");
            Usuario aux = stukemonDAO.getUsuarioByNombre(user.getNombreuser());
            System.out.println("·Nuevos datos:");
            System.out.println(aux);
        } catch (Excepcion ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void combate(StukemonDAO stukemonDAO, Usuario user1, Pokedex pokedex1, Usuario user2, Pokedex pokedex2) throws SQLException {
        try {
            stukemonDAO.lucha(user1, pokedex1, user2, pokedex2);
        } catch (Excepcion ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void curarPokemonUser(StukemonDAO stukemonDAO, Usuario user, Pokedex pokedex) throws SQLException {
        try {
            stukemonDAO.curarPokemonUser(user, pokedex);
        } catch (Excepcion ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void mejorarVidaPc(StukemonDAO stukemonDAO, Usuario user, Pokedex pokedex) throws SQLException {
        try {
            stukemonDAO.mejorarPokedexUser(user, pokedex);
        } catch (Excepcion ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void obtenerListaPokedexByUser(StukemonDAO stukemonDAO, Usuario user) throws SQLException {
        try {
            System.out.println("·Datos de las pokedex:");
            stukemonDAO.getPokedexByUser(user);
        } catch (Excepcion ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void obtenerRankingUser(StukemonDAO stukemonDAO) throws SQLException {
        List<RankingUsuarios> rankingUsuario = stukemonDAO.getRankingUsuarios();
        for (RankingUsuarios rankUsus : rankingUsuario) {
            System.out.println(rankUsus);
        }
    }

    public static void obtenerRankingPokemon(StukemonDAO stukemonDAO) throws SQLException {
        List<RankingPokemon> rankingPokemon = stukemonDAO.getRankingPokemon();
        for (RankingPokemon rankPoke : rankingPokemon) {
            System.out.println(rankPoke);
        }
    }

}
