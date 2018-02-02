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
public class Stukemon_Go {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        StukemonDAO stukemonDAO = new StukemonDAO();

//*************************** Datos Iniciales ********************************************************** 
        //Se regalan 20 pokeballs que se definen en la clase usuario 
        Usuario user1 = new Usuario("Lucas", "Ro34", "Splau");
        Usuario user2 = new Usuario("Julio", "Lol21", "Sagrada Familia");
        Usuario user3 = new Usuario("Luis", "1234", "Anec Blau");
        Usuario user4 = new Usuario("Antonia", "12345", "Anec Blau");
        Usuario user5 = new Usuario("Maria", "125", "Frangoal");
        Usuario user6 = new Usuario("Carlos", "1125", "Frangoal");
        Usuario user7 = new Usuario("David", "Q123", "Sagrada Familia");
        Usuario user8 = new Usuario("Martin", "456Q", "Stucom");
        Usuario user9 = new Usuario("Pedro", "4561Q", "Torneo");
        Usuario user10 = new Usuario("Monica", "1256Q", "Torneo");
        Usuario user11 = new Usuario("Jaime", "456456Q", "Torneo");
        Usuario user12 = new Usuario("Victor", "069456Q", "Torneo");
        Usuario user13 = new Usuario("Sara", "12456", "Torneo");
        Usuario user14 = new Usuario("Julia", "41257R", "Torneo");

        Pokemon pk1 = new Pokemon("Pikachu", "Electrico", 15, 30, "C/Pelai");
        Pokemon pk2 = new Pokemon("Charizar", "Fuego", 20, 50, "Mercadona");
        Pokemon pk3 = new Pokemon("Bulbasur", "Planta", 20, 100, "Splau");
        Pokemon pk4 = new Pokemon("Ivysaur", "Agua", 20, 50, "Barnasud");
        Pokemon pk5 = new Pokemon("Arbok", "Veneno", 20, 60, "Stucom");
        Pokemon pk6 = new Pokemon("Charmaider", "Fuego", 30, 60, "Splau");

        Pokeparada pokepa1 = new Pokeparada("Losa2", "Sagrada Familia", 2, 4);
        Pokedex pokedex1 = new Pokedex(pk1, LocalDate.now(), pk1.getPc(), 20, pk1.getVida());
        Pokedex pokedex2 = new Pokedex(pk2, LocalDate.now(), pk2.getPc(), pk2.getVida(), pk2.getVida());
        Pokedex pokedex3 = new Pokedex(pk3, LocalDate.now(), pk3.getPc(), pk3.getVida(), 20);
        Pokedex pokedex4 = new Pokedex(pk4, LocalDate.now(), pk4.getPc(), pk4.getVida(), pk4.getVida());
        Pokedex pokedex5 = new Pokedex(pk5, LocalDate.now(), pk5.getPc(), pk5.getVida(), pk5.getVida());

//******************************************************************************************************
        // Conectamos a la base de datos
        try {
            System.out.println("************************************************************");
            System.out.println("-- Testeando conexión con la base de datos --");
            stukemonDAO.conectar();
            System.out.println("·Establecida la conexión.");

            System.out.println("************************************************************");
            System.out.println("-- Añadiendo datos para que funcione la aplicacion --");
            altaUser(stukemonDAO, user2);
            altaUser(stukemonDAO, user3);
            altaUser(stukemonDAO, user4);
            altaUser(stukemonDAO, user5);
            altaUser(stukemonDAO, user6);
            altaUser(stukemonDAO, user7);
            altaUser(stukemonDAO, user8);
            altaUser(stukemonDAO, user9);
            altaUser(stukemonDAO, user10);
            altaUser(stukemonDAO, user11);
            altaUser(stukemonDAO, user12);
            altaUser(stukemonDAO, user13);
            altaUser(stukemonDAO, user14);

            altaPokemon(stukemonDAO, pk2);
            altaPokemon(stukemonDAO, pk3);
            altaPokemon(stukemonDAO, pk4);
            altaPokemon(stukemonDAO, pk5);
            altaPokemon(stukemonDAO, pk6);

            System.out.println("************************************************************");

            //Registramos usuario
            System.out.println("************************************************************");
            System.out.println("-- Testeando insert user " + user1.getNombreuser() + " --");
            //Para poder hacer punto de - curar pokemon -
            user1.setPociones(user1.getPociones() + 2);
            //Para poder hacer punto de - mejorar vida y pc pokemon -
            user1.setMonedas(user1.getMonedas()+200);
            altaUser(stukemonDAO, user1);

            //Buscamos usuario por nombreuser
            System.out.println("************************************************************");
            System.out.println("-- Testeando obtener usuario por el username: " + user2.getNombreuser() + " --");
            obtenerUsuario(stukemonDAO, user2.getNombreuser());

            //Registramos pokemon
            System.out.println("************************************************************");
            System.out.println("-- Testeando insert pokemon " + pk1.getNombre() + " --");
            altaPokemon(stukemonDAO, pk1);

            //Registramos pokeparada
            System.out.println("************************************************************");
            System.out.println("-- Testeando insert pokeparada " + pokepa1.getNombre() + " --");
            altaPokeparada(stukemonDAO, pokepa1);

            //Validamos login usuario
            System.out.println("************************************************************");
            System.out.println("-- Testeando login usuario: " + user2.getNombreuser() + " --");
            validarUser(stukemonDAO, user2.getNombreuser(), user2.getPassword());

            //Modificamos lugar usuario
            System.out.println("************************************************************");
            System.out.println("-- Testeando modificar lugar del usuario: " + user2.getNombreuser() + " --");
            System.out.println("·Datos Actuales:");
            System.out.println(user2);
            System.out.println("·Estableciendo lugar en CorteIngles");
            user2.setLugar("CorteIngles");
            modificarLugarUser(stukemonDAO, user2);

            //Modificamos lugar pokemon
            System.out.println("************************************************************");
            System.out.println("-- Testeando modificar lugar del pokemon: " + pk1.getNombre() + " --");
            System.out.println("·Datos Actuales:");
            System.out.println(pk1);
            System.out.println("·Estableciendo lugar en Gran Via 2");
            pk1.setLugar("Gran Via 2");
            modificarLugarPokemon(stukemonDAO, pk1);

            //Listado pokemon que estan en el mismo lugar que un usuario dado
            System.out.println("************************************************************");
            System.out.println("-- Testeando obtener listado pokemon que estan en el mismo lugar que el usuario:" + user1.getNombreuser() + " --");
            obtenerListaPokemonByUserPlace(stukemonDAO, user1);

            //Listado pokemon que estan en el mismo lugar que un usuario dado - Fallo ningun pokemon en el mismo lugar
            System.out.println("************************************************************");
            System.out.println("-- Testeando obtener listado pokemon que estan en el mismo lugar que el usuario:" + user2.getNombreuser() + ", Fallo ningun pokemon en el mismo lugar --");
            obtenerListaPokemonByUserPlace(stukemonDAO, user2);

            //Capturar pokemon recibinedo usuario y pokemon
            System.out.println("************************************************************");
            System.out.println("-- Testeando capturar pokemon, Usuario: " + user1.getNombreuser() + " / Pokemon: " + pk3.getNombre() + " --");
            capturarPokemon(stukemonDAO, user1, pk3);
            //Repetimos el metodo para comprobar que el pokemon ha cambiado de lugar
            System.out.println("-- Testeando si cambia de lugar tanto si se captura como si escapa el pokemon --");
            capturarPokemon(stukemonDAO, user1, pk3);

            //Capturar pokemon recibinedo usuario y pokemon - Fallo no estan en el mismo sitio
            System.out.println("************************************************************");
            System.out.println("-- Testeando capturar pokemon - Fallo no estan en el mismo sitio, Usuario: " + user1.getNombreuser() + " / Pokemon: " + pk2.getNombre() + " --");
            capturarPokemon(stukemonDAO, user1, pk2);
            //Repetimos el metodo para comprobar que el pokemon ha cambiado de lugar
            System.out.println("-- Testeando si cambia de lugar tanto si se captura como si escapa el pokemon --");
            capturarPokemon(stukemonDAO, user1, pk2);

            //Liberar pokemon recibiendo usuario y pokemon
            System.out.println("************************************************************");
            try {
                stukemonDAO.insertarPokedex(pokedex2, user1);
                user1.setPokedex((ArrayList<Pokedex>) stukemonDAO.getPokedexByUser(user1));
            } catch (Excepcion ex) {
                System.out.println(ex.getMessage());
            }
            System.out.println("-- Testeando liberar pokemon del usuario: " + user1.getNombreuser() + " --");
            liberarPokemon(stukemonDAO, user1, user1.getPokedex().get(0));

            //Liberar pokemon recibiendo usuario y pokemon
            System.out.println("************************************************************");
            try {
                stukemonDAO.insertarPokedex(pokedex4, user2);
                user2.setPokedex((ArrayList<Pokedex>) stukemonDAO.getPokedexByUser(user2));
            } catch (Excepcion ex) {
                System.out.println(ex.getMessage());
            }
            System.out.println("-- Testeando liberar pokemon: " + user1.getNombreuser() + " - Fallo pokemon no pertenece al usuario --");
            liberarPokemon(stukemonDAO, user1, user2.getPokedex().get(0));

            //Listado usuarios que estan en el mismo lugar que un usuario dado.
            System.out.println("************************************************************");
            System.out.println("-- Testeando obtener listado usuarios que estan en el mismo lugar que el usuario: " + user5.getNombreuser() + " --");
            obtenerListaUsersByUserPlace(stukemonDAO, user5);

            //Listado pokeparadas que estan en el mismo lugar que un usuario dado.
            System.out.println("************************************************************");
            System.out.println("-- Testeando obtener listado pokeparadas que estan en el mismo lugar que el usuario: " + user7.getNombreuser() + " --");
            obtenerListaPokeparadaByUserPlace(stukemonDAO, user7);

            //Coger regalos pokeparadas recibiendo usuario y pokeparada
            System.out.println("************************************************************");
            System.out.println("-- Testeando obtener regalos de la pokeparada: " + pokepa1.getNombre() + " / usuario: " + user7.getNombreuser() + " --");
            System.out.println("·Datos Actuales:");
            System.out.println(user7);
            System.out.println("·Recoginedo los regalos de la pokeparada");
            obtenerRegalos(stukemonDAO, user7, pokepa1);

            //Coger regalos pokeparadas recibiendo usuario y pokeparada - Fallo
            System.out.println("************************************************************");
            System.out.println("-- Testeando obtener regalos de la pokeparada: " + pokepa1.getNombre() + " / usuario: " + user2.getNombreuser() + " - Fallo no estan en el mismo lugar --");
            System.out.println("·Datos Actuales:");
            System.out.println(user2);
            System.out.println("·Recoginedo los regalos de la pokeparada");
            obtenerRegalos(stukemonDAO, user2, pokepa1);

            //Combate entre dos pokemon de dos usuarios
            System.out.println("************************************************************");
            try {
                stukemonDAO.insertarPokedex(pokedex4, user3);
                user3.setPokedex((ArrayList<Pokedex>) stukemonDAO.getPokedexByUser(user3));
                stukemonDAO.insertarPokedex(pokedex5, user4);
                user4.setPokedex((ArrayList<Pokedex>) stukemonDAO.getPokedexByUser(user4));
            } catch (Excepcion ex) {
                System.out.println(ex.getMessage());
            }
            System.out.println("-- Testeando obtener combate entre dos usuarios con dos pokemon determinados --");
            combate(stukemonDAO, user3, user3.getPokedex().get(0), user4, user4.getPokedex().get(0));

            //Curar a un pokemon de un usuario determinado
            System.out.println("************************************************************");
            try {
                stukemonDAO.insertarPokedex(pokedex3, user1);
                user1.setPokedex((ArrayList<Pokedex>) stukemonDAO.getPokedexByUser(user1));
            } catch (Excepcion ex) {
                System.out.println(ex.getMessage());
            }
            System.out.println("-- Testeando curar pokemon del Usuario: " + user1.getNombreuser() + "/ Pokemon: " + user1.getPokedex().get(0).getPokemon().getNombre() + " --");
            curarPokemonUser(stukemonDAO, user1, user1.getPokedex().get(0));

            //Curar a un pokemon de un usuario
            System.out.println("************************************************************");
            try {
                stukemonDAO.insertarPokedex(pokedex3, user6);
                user6.setPokedex((ArrayList<Pokedex>) stukemonDAO.getPokedexByUser(user6));
            } catch (Excepcion ex) {
                System.out.println(ex.getMessage());
            }
            System.out.println("-- Testeando curar pokemon, Fallo no tiene pociones - Usuario: " + user6.getNombreuser() + "/ Pokemon: " + user6.getPokedex().get(0).getPokemon().getNombre() + " --");
            curarPokemonUser(stukemonDAO, user6, user6.getPokedex().get(0));

            //Mejorar vida y pc de un pokemon de un usuario determinado
            System.out.println("************************************************************");
            try {
                stukemonDAO.insertarPokedex(pokedex1, user1);
                user1.setPokedex((ArrayList<Pokedex>) stukemonDAO.getPokedexByUser(user1));
            } catch (Excepcion ex) {
                System.out.println(ex.getMessage());
            }
            System.out.println("-- Testeando mejorar vida y pc del Usuario: " + user1.getNombreuser() + " / Pokemon: " + user1.getPokedex().get(0).getPokemon().getNombre() + " --");
            mejorarVidaPc(stukemonDAO, user1, user1.getPokedex().get(0));

            //Obtener la pokedex de un usuario
            System.out.println("************************************************************");
            System.out.println("-- Testeando obtener listado pokedex de el usuario: " + user1.getNombreuser() + " --");
            obtenerListaPokedexByUser(stukemonDAO, user1);

            //Obtener ranking de los usuarios que tengan mas pokemon
            System.out.println("************************************************************");
            System.out.println("-- Testeando obtener ranking usuarios con mas pokemon --");
            obtenerRankingUser(stukemonDAO);

            System.out.println("************************************************************");
            try {
                //Obtener ranking de los 10 mejores pokemon segun el numero de batallas ganadas
                stukemonDAO.insertarPokedex(pokedex1, user9);
                user9.setPokedex((ArrayList<Pokedex>) stukemonDAO.getPokedexByUser(user9));

                stukemonDAO.insertarPokedex(pokedex2, user10);
                user10.setPokedex((ArrayList<Pokedex>) stukemonDAO.getPokedexByUser(user10));

                stukemonDAO.insertarPokedex(pokedex3, user11);
                user11.setPokedex((ArrayList<Pokedex>) stukemonDAO.getPokedexByUser(user11));

                stukemonDAO.insertarPokedex(pokedex4, user12);
                user12.setPokedex((ArrayList<Pokedex>) stukemonDAO.getPokedexByUser(user12));

                stukemonDAO.insertarPokedex(pokedex5, user13);
                user13.setPokedex((ArrayList<Pokedex>) stukemonDAO.getPokedexByUser(user13));

                stukemonDAO.insertarPokedex(pokedex2, user14);
                user14.setPokedex((ArrayList<Pokedex>) stukemonDAO.getPokedexByUser(user14));

            } catch (Excepcion ex) {
                System.out.println(ex.getMessage());
            }
            combate(stukemonDAO, user9, user9.getPokedex().get(0), user10, user10.getPokedex().get(0));
            combate(stukemonDAO, user11, user11.getPokedex().get(0), user12, user12.getPokedex().get(0));
            combate(stukemonDAO, user13, user13.getPokedex().get(0), user14, user14.getPokedex().get(0));
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
            System.out.println("·Pokemon Liberado y añadidas 25 pokecoins");
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
            System.out.println("Combate Realizado con exito");
        } catch (Excepcion ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void curarPokemonUser(StukemonDAO stukemonDAO, Usuario user, Pokedex pokedex) throws SQLException {
        try {
            stukemonDAO.curarPokemonUser(user, pokedex);
            System.out.println("·Pokemon Curado");
        } catch (Excepcion ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void mejorarVidaPc(StukemonDAO stukemonDAO, Usuario user, Pokedex pokedex) throws SQLException {
        try {
            stukemonDAO.mejorarPokedexUser(user, pokedex);
            System.out.println("·Vida Mejorada");
        } catch (Excepcion ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void obtenerListaPokedexByUser(StukemonDAO stukemonDAO, Usuario user) throws SQLException {
        try {
            System.out.println("·Datos de las pokedex:");
            List<Pokedex> pokedex = stukemonDAO.getPokedexByUser(user);
            for (Pokedex pokedexUser : pokedex) {
                System.out.println("Id: " + pokedexUser.getId() + " - NombrePokemon: " + pokedexUser.getPokemon().getNombre()
                        + " - FechaCaptura: " + pokedexUser.getFecha() + " - Pc: " + pokedexUser.getPc()
                        + " - VidaActual: " + pokedexUser.getVidaActual() + " - VidaMaxima: " + pokedexUser.getMaximaVida());
            }
        } catch (Excepcion ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void obtenerRankingUser(StukemonDAO stukemonDAO) throws SQLException {
        try {
            List<RankingUsuarios> rankingUsuario = stukemonDAO.getRankingUsuarios();

            for (RankingUsuarios rankUsus : rankingUsuario) {
                System.out.println(rankUsus);
            }
        } catch (Excepcion ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void obtenerRankingPokemon(StukemonDAO stukemonDAO) throws SQLException {
        List<RankingPokemon> rankingPokemon = stukemonDAO.getRankingPokemon();
        for (RankingPokemon rankPoke : rankingPokemon) {
            System.out.println(rankPoke);
        }
    }

}
