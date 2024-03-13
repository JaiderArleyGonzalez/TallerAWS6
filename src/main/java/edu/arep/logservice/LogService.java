package edu.arep.logservice;
import com.mongodb.client.MongoDatabase;
import edu.arep.mongodb.*;
import java.time.LocalDate;
import java.util.List;
import static spark.Spark.*;

public class LogService {
    public static void main(String[] args) {
        port(getPort());
        get("/logservice", (req, res) -> {
            String message = req.queryParams("msg");
            return sendMessage(message);
        });
    }
    public static List<String> sendMessage(String message){
        MongoDatabase database = MongoUtil.getDB();
        MessageDAO messageDAO = new MessageDAO(database);
        if (message == null || message.isEmpty() || message.equals("null")) {
            return messageDAO.listMessage();
        }
        messageDAO.addMessage(message, LocalDate.now());
        return messageDAO.listMessage();
    }
    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 35001;
    }
}
