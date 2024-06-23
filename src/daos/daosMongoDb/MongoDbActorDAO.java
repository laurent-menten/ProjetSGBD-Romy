package daos.daosMongoDb;

import beans.Actor;
import daos.interfaces.IActorDAO;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class MongoDbActorDAO implements IActorDAO {
    private final MongoCollection<Document> collection;

    public MongoDbActorDAO(String connectionString, String dbName) {
        MongoClient mongoClient = MongoClients.create(connectionString);
        MongoDatabase database = mongoClient.getDatabase(dbName);
        this.collection = database.getCollection("actors");
    }

      
    public void create(Actor obj) {
        Document doc = new Document("_id", new ObjectId())
                .append("id", obj.getId())
                .append("firstname", obj.getFirstname())
                .append("lastname", obj.getLastname())
                .append("birthdate", obj.getBirthdate().toString());
        collection.insertOne(doc);
    }

      
    public void update(Actor obj) {
        Document updatedDoc = new Document("id", obj.getId())
                .append("firstname", obj.getFirstname())
                .append("lastname", obj.getLastname())
                .append("birthdate", obj.getBirthdate().toString());
        collection.updateOne(eq("id", obj.getId()), new Document("$set", updatedDoc));
    }

      
    public void delete(Actor obj) {
        deleteById(obj.getId());
    }

      
    public void deleteById(Integer id) {
        collection.deleteOne(eq("id", id));
    }

      
    public Actor findById(Integer id) {
        Document doc = collection.find(eq("id", id)).first();
        return doc != null ? docToActor(doc) : null;
    }

      
    public Actor[] findAll() {
        List<Actor> actors = new ArrayList<>();
        for (Document doc : collection.find()) {
            actors.add(docToActor(doc));
        }
        return actors.toArray(new Actor[0]);
    }

    private Actor docToActor(Document doc) {
        return new Actor(
                doc.getInteger("id"),
                doc.getString("firstname"),
                doc.getString("lastname"),
                LocalDate.parse(doc.getString("birthdate"), DateTimeFormatter.ISO_DATE));
    }
}
