package edu.arep.mongodb;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;

public class MongoUtil {
    private static final String CONNECTION_STRING = "mongodb://ec2-3-85-129-106.compute-1.amazonaws.com:27017";
    private static final String DATABASE_NAME = "messages";

    /**
     * Obtiene una instancia de la base de datos MongoDB.
     * @return La instancia de la base de datos MongoDB.
     */
    public static MongoDatabase getDB() {
        MongoClient client = MongoClients.create(CONNECTION_STRING);
        return client.getDatabase(DATABASE_NAME);
    }
}