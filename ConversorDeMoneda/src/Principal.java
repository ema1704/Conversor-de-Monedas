import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class Principal{

    private static final String tasaDeCambio = "https://api.exchangerate-api.com/v4/latest/USD";

    public static void main(String[] args) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(tasaDeCambio))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                Gson gson = new Gson();
                JsonObject ratesJson = JsonParser.parseString(response.body()).getAsJsonObject().getAsJsonObject("rates");

                double usdAars = ratesJson.get("ARS").getAsDouble();
                double usdAeur = ratesJson.get("EUR").getAsDouble();
                double usdAbrl = ratesJson.get("BRL").getAsDouble();


                Scanner scanner = new Scanner(System.in);
                boolean salir = false;
                while (!salir) {
                    System.out.println("BIENVENIDOS AL CONVERSOR DE MONEDA");
                    System.out.println("Opciones");
                    System.out.println("1. USD a ARS");
                    System.out.println("2. USD a EUR");
                    System.out.println("3. USD a BRL");
                    System.out.println("4. Salir");
                    System.out.print("Ingrese su eleccion: ");
                    int opcion = scanner.nextInt();

                    switch (opcion) {
                        case 1:
                            System.out.print("Intruduzca el importe en USD: ");
                            double cantidadUSD = scanner.nextDouble();
                            double convertidoARS = cantidadUSD * usdAars;
                            System.out.println("Cantidad convertida en ARS: " + convertidoARS);
                            break;
                        case 2:
                            System.out.print("Intruduzca el importe en USD: ");
                            double cantidadUSD2 = scanner.nextDouble();
                            double convertidoEUR = cantidadUSD2 * usdAeur;
                            System.out.println("Cantidad convertida en EUR: " + convertidoEUR);
                            break;
                        case 3:
                            System.out.print("Intruduzca el importe en USD: ");
                            double cantidadUSD3 = scanner.nextDouble();
                            double convertidoBRL = cantidadUSD3 * usdAbrl;
                            System.out.println("Cantidad convertida en BRL: " + convertidoBRL);
                            break;
                        case 4:
                            salir = true;
                            System.out.println("Finalizando el programa");
                            break;
                        default:
                            System.out.println("Elección no válida. Inténtalo de nuevo.");
                    }
                }
            } else {
                System.out.println("No se encontro el tipo de cambio: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e){

        }
    }
}

