package edu.arep;

import static spark.Spark.*;

public class LogService {
    public static void main(String[] args) {
        port(5000);
        get("/logservice", (req, res) -> "{\"msg\":\"primer mensaje, 24-02-2024 16:45:45\"}");
    }
}
