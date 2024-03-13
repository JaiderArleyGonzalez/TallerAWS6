package edu.arep.mongodb;
import com.mongodb.client.*;
import org.bson.Document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import com.mongodb.client.model.Sorts;
public class MessageDAO {
    private final MongoCollection<Document> messagesCollection;
    public MessageDAO(MongoDatabase database){
        this.messagesCollection = database.getCollection("messages");
    }
    public void addMessage(String message, LocalDate date) {
        Document newMessage = new Document("message", message)
                .append("date", date);
        messagesCollection.insertOne(newMessage);
    }

    public List<String> listMessage() {
        FindIterable<Document> messages;
        messages = messagesCollection.find().sort(Sorts.descending("_id")).limit(10);
        List<String> messagesReturn = new ArrayList<>();
        for (Document message : messages) {
            messagesReturn.add(message.toJson());
        }
        return messagesReturn;
    }

    public void deleteMessage(String message) {
        messagesCollection.deleteOne(eq("message", message));
    }

}
