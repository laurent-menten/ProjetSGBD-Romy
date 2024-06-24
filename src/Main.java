import beans.Actor;
import beans.Movie;
import daos.daosMongoDb.MongoDbDAOFactory;
import daos.interfaces.IActorDAO;
import daos.interfaces.IDAOFactory;
import daos.interfaces.IMovieDAO;

import java.sql.SQLException;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) throws SQLException {

        // Exemple avec mongoDB
        IDAOFactory daoFactory = new MongoDbDAOFactory("mongodb://localhost:27017" );

        // ---

        IActorDAO actorDAO = daoFactory.getActorDAO();

        actorDAO.create(new Actor(1,"Orlando", "Bloom", LocalDate.of(1977, 1,13)));
        actorDAO.create(new Actor(2,"Tom", "Elis", LocalDate.of(1978, 11,17)));
        actorDAO.create(new Actor(3,"Gillian", "Anderson", LocalDate.of(1968, 8,9)));
        actorDAO.create(new Actor(4,"Brandon", "Fraser", LocalDate.of(1968, 12,3)));
        actorDAO.create(new Actor(5,"Colin", "Morgan", LocalDate.of(1986, 1,1)));
        actorDAO.create(new Actor(6,"Michael", "Douglas", LocalDate.of(1944, 9,25)));
        affActors(actorDAO);

        // ---

        IMovieDAO movieDAO = daoFactory.getMovieDAO();

        movieDAO.create(new Movie(1, "Howl's moving castle", LocalDate.of(2004, 5,1), new int[0]));
        movieDAO.create(new Movie(2, "Sleeping beauty", LocalDate.of(1959, 5,1), new int[0]));
        movieDAO.create(new Movie(3, "The game", LocalDate.of(1997, 5,1), new int[]{ 6 }));
        movieDAO.create(new Movie(4, "The Mummy", LocalDate.of(1999, 5,1), new int[]{ 4 }));
        movieDAO.create(new Movie(5, "Kingdom of heaven", LocalDate.of(2005, 5,1), new int[]{ 1 }));
        affMovies(movieDAO);

        // ---

        // avec gestion de transaction
        daoFactory.beginTransaction();
        movieDAO.deleteById(3);
        movieDAO.create(new Movie(6, "Troy", LocalDate.of(2004, 5,1), new int[]{ 1 }));
        daoFactory.commitTransaction();
        affMovies(movieDAO);

        // avec gestion de transaction
        daoFactory.beginTransaction();
        movieDAO.deleteById(7);
        affMovies(movieDAO);
        daoFactory.rollbackTransaction();
        affMovies(movieDAO);

        // affichage film spécifique
        Movie mov = movieDAO.findById(4);
        System.out.println(mov);

        movieDAO.delete(mov);
        affMovies(movieDAO);

        System.out.println("-----------------------------------");

        daoFactory.close();
    }

    private static void affMovies(IMovieDAO movieDAO) throws SQLException {
        System.out.println("--- tous les film :");
        for (Movie movie : movieDAO.findAll()) {
            System.out.println(movie);
        }
    }

    private static void affActors(IActorDAO actorDAO) throws SQLException {
        System.out.println("--- tous les acteurs :");
        for (Actor actor: actorDAO.findAll()) {
            System.out.println(actor);
        }
    }
}