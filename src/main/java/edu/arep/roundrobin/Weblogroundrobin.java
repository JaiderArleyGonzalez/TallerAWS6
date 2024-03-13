package edu.arep.roundrobin;

import static spark.Spark.*;
/**
 * Clase principal que inicia el balanceador de carga Round Robin.
 */
public class Weblogroundrobin
{
    private static String PORT = "35003";

    /**
     * Método principal que inicia el balanceador de carga Round Robin.
     * @param args Los argumentos de línea de comandos.
     */
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

    /**
     * Obtiene el puerto en el que se iniciará el servicio.
     * @return El puerto en el que se iniciará el servicio.
     */
    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 8080;
    }
}
