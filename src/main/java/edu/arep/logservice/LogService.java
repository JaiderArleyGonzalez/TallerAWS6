package edu.arep.logservice;
import com.mongodb.client.MongoDatabase;
import edu.arep.mongodb.*;
import java.time.LocalDate;
import java.util.List;
import static spark.Spark.*;

/**
 * Clase que proporciona un servicio web para el registro de mensajes en un chat.
 */
public class LogService {
    /**
     * Inicia el servicio de registro de mensajes en el puerto especificado.
     * @param args Argumentos de línea de comandos.
     */
    public static void main(String[] args) {
        port(getPort());
        get("/logservice", (req, res) -> {
            String message = req.queryParams("msg");
            return sendMessage(message);
        });
    }
    /**
     * Envia un mensaje al servicio de registro de mensajes.
     * @param message El mensaje a enviar.
     * @return Una lista de mensajes que incluye el mensaje recién agregado (si se proporciona).
     */
    public static List<String> sendMessage(String message){
        MongoDatabase database = MongoUtil.getDB();
        MessageDAO messageDAO = new MessageDAO(database);
        if (message == null || message.isEmpty() || message.equals("null")) {
            return messageDAO.listMessage();
        }
        messageDAO.addMessage(message, LocalDate.now());
        return messageDAO.listMessage();
    }

    /**
     * Obtiene el puerto en el que se iniciará el servicio.
     * @return El puerto en el que se iniciará el servicio.
     */
    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 35001;
    }
}
