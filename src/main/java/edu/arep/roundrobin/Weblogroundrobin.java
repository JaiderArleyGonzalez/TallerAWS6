package edu.arep.roundrobin;

import static spark.Spark.*;

public class Weblogroundrobin
{
    private static String PORT = "35003";
    public static void main( String[] args )
    {
        port(getPort());
        staticFiles.location("/public");
        if (PORT.equals("35001")){
            PORT = "35002";
        } else if (PORT.equals("35002")) {
            PORT = "35003";
        }else {
            PORT = "35001";
        }
        get("/log", (req, res) -> RRInvoker.Invoke(req.queryString(), PORT));
    }
    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 8080;
    }
}
