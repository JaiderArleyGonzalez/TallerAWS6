package edu.arep.roundrobin;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Clase que invoca un servicio web para realizar solicitudes GET.
 */
public class RRInvoker {

    private static final String USER_AGENT = "Mozilla/5.0";

    private static final String GET_URL = "http://ec2-3-85-129-106.compute-1.amazonaws.com:";

    /**
     * Realiza una solicitud GET al servicio de registro de mensajes y devuelve la respuesta.
     * @param query El parámetro de consulta.
     * @param PORT El puerto en el que se encuentra el servicio.
     * @return La respuesta del servicio de registro de mensajes.
     * @throws IOException Si ocurre un error de conexión o lectura.
     */
    public static String Invoke(String query, String PORT) throws IOException {

        URL obj = new URL(GET_URL + PORT +"/logservice?"+ query);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);

        //The following invocation perform the connection implicitly before getting the code
        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);
        StringBuffer response = new StringBuffer();
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result
            System.out.println(response.toString());
        } else {
            System.out.println("GET request not worked");
        }
        System.out.println("GET DONE");
        return response.toString();
    }

}
