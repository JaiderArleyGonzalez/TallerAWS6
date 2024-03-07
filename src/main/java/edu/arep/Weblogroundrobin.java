package edu.arep;
import static spark.Spark.*;

public class Weblogroundrobin
{
    public static void main( String[] args )
    {
        staticFiles.location("/public");
        get("/log", (req, res) -> RRInvoker.Invoke());
    }
}
