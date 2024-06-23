package daos.daosMongoDb;

import beans.Movie;
import daos.interfaces.IMovieDAO;
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

public class MongoDbMovieDAO implements IMovieDAO {
    private final MongoCollection<Document> collection;

    public MongoDbMovieDAO(String connectionString, String dbName) {
        MongoClient mongoClient = MongoClients.create(connectionString);
        MongoDatabase database = mongoClient.getDatabase(dbName);
        this.collection = database.getCollection("movies");
    }

    @Override
    public void create(Movie obj) {
        Document doc = new Document("_id", new ObjectId())
                .append("id", obj.getId())
                .append("title", obj.getTitle())
                .append("releaseDate", obj.getReleaseDate().toString());
        collection.insertOne(doc);
    }

    @Override
    public void update(Movie obj) {
        Document updatedDoc = new Document("id", obj.getId())
                .append("title", obj.getTitle())
                .append("releaseDate", obj.getReleaseDate().toString());
        collection.updateOne(eq("id", obj.getId()), new Document("$set", updatedDoc));
    }

    @Override
    public void delete(Movie obj) {
        deleteById(obj.getId());
    }

    @Override
    public void deleteById(Integer id) {
        collection.deleteOne(eq("id", id));
    }

    @Override
    public Movie findById(Integer id) {
        Document doc = collection.find(eq("id", id)).first();
        return doc != null ? docToMovie(doc) : null;
    }

    @Override
    public Movie[] findAll() {
        List<Movie> movies = new ArrayList<>();
        for (Document doc : collection.find()) {
            movies.add(docToMovie(doc));
        }
        return movies.toArray(new Movie[0]);
    }

    private Movie docToMovie(Document doc) {
        return new Movie(
                doc.getInteger("id"),
                doc.getString("title"),
                LocalDate.parse(doc.getString("releaseDate"), DateTimeFormatter.ISO_DATE),
                new int[0]);  // Assuming you have an array of integers; adjust as needed
    }
}
