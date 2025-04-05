import java.io.*; // импорт пакета, содержащего классы для ввода/вывода
import java.net.*; // импорт пакета, содержащего классы для работы в сети

public class Client {
    public static void main(String[] arg) {
        Socket clientSocket = null;
        ObjectOutputStream coos = null;
        ObjectInputStream cois = null;
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

        try {
            System.out.println("Connecting to server...");
            clientSocket = new Socket("127.0.0.1", 2525);
            System.out.println("Connection established.");

            coos = new ObjectOutputStream(clientSocket.getOutputStream());
            cois = new ObjectInputStream(clientSocket.getInputStream());

            System.out.println("Enter any string to send to server \n\t('quite' − program terminate)");
            String clientMessage;

            while (true) {


                coos.writeObject("09");

                System.out.println("~server~: " + cois.readObject());
                System.out.println("---------------------------");
            }
        } catch (EOFException e) {
            System.err.println("Server closed the connection: " + e.getMessage());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            // Закрытие потоков и сокета
            try {
                if (coos != null) coos.close();
                if (cois != null) cois.close();
                if (clientSocket != null) clientSocket.close();
                stdin.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
