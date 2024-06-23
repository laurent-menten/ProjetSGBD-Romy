package daos.daosMongoDb;

import daos.interfaces.IActorDAO;
import daos.interfaces.IDAOFactory;
import daos.interfaces.IMovieDAO;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoDbDAOFactory implements IDAOFactory {

    // ---------------------------------------------------------------
    // Paramètres "variables"
    // ---------------------------------------------------------------
    private IActorDAO actorDAO;
    private IMovieDAO movieDAO;

    // ---------------------------------------------------------------
    // Paramètres "statiques"
    // ---------------------------------------------------------------
    private static MongoClient mongoClient;
    private static MongoDatabase database;

    private static final String dbName = "Movies";

    public MongoDbDAOFactory(String connectionString){
        mongoClient = getMongoClient(connectionString);
        database = mongoClient.getDatabase(dbName);
    }

    protected MongoClient getMongoClient(String connectionString) {
        if (mongoClient == null) {
            mongoClient = MongoClients.create(connectionString);
        }
        return mongoClient;
    }

    public IActorDAO getActorDAO() {
        if (actorDAO == null) actorDAO = new MongoDbActorDAO(mongoClient, dbName);
        return actorDAO;
    }

    public IMovieDAO getMovieDAO() {
        if (movieDAO == null) movieDAO = new MongoDbMovieDAO(mongoClient, dbName);
        return movieDAO;
    }

    // ---------------------------------------------------------------
    // Gestion des Transactions (not applicable in the same way for MongoDB)
    // ---------------------------------------------------------------
    public void beginTransaction() {
        // MongoDB transactions are typically handled differently and are often not necessary for many applications.
    }

    public void commitTransaction() {
        // Commit transaction logic if using multi-document transactions
    }

    public void rollbackTransaction() {
        // Rollback transaction logic if using multi-document transactions
    }

    public void close() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }
}
