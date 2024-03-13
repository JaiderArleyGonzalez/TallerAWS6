package edu.arep.mongodb;
import com.mongodb.client.*;
import org.bson.Document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import com.mongodb.client.model.Sorts;

/**
 * Clase que proporciona métodos para interactuar con la base de datos MongoDB para el registro de mensajes.
 */
public class MessageDAO {
    private final MongoCollection<Document> messagesCollection;

    /**
     * Constructor de la clase MessageDAO.
     * @param database La base de datos MongoDB.
     */
    public MessageDAO(MongoDatabase database){
        this.messagesCollection = database.getCollection("messages");
    }

    /**
     * Agrega un mensaje a la base de datos MongoDB.
     * @param message El mensaje a agregar.
     * @param date La fecha del mensaje.
     */
    public void addMessage(String message, LocalDate date) {
        Document newMessage = new Document("message", message)
                .append("date", date);
        messagesCollection.insertOne(newMessage);
    }

    /**
     * Lista los mensajes almacenados en la base de datos MongoDB.
     * @return Una lista de mensajes en formato JSON.
     */
    public List<String> listMessage() {
        FindIterable<Document> messages;
        messages = messagesCollection.find().sort(Sorts.descending("_id")).limit(10);
        List<String> messagesReturn = new ArrayList<>();
        for (Document message : messages) {
            messagesReturn.add(message.toJson());
        }
        return messagesReturn;
    }

    /**
     * Elimina un mensaje de la base de datos MongoDB.
     * @param message El mensaje a eliminar.
     */
    public void deleteMessage(String message) {
        messagesCollection.deleteOne(eq("message", message));
    }

}
