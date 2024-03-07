package edu.arep.mongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import com.mongodb.client.FindIterable;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

public class MessageDAO {
    private final MongoCollection<Document> messagesCollection;
    public MessageDAO(MongoDatabase database){
        this.messagesCollection = database.getCollection("messages");
    }
    public void addMessage(String message) {
        Document newUser = new Document("message", message);
        messagesCollection.insertOne(newUser);
    }

    public void listMessage() {
        FindIterable<Document> messages = messagesCollection.find();
        for (Document message : messages) {
            System.out.println(message.toJson());
        }
    }



    public void deleteUser(String message) {
        messagesCollection.deleteOne(eq("message", message));
    }

}
