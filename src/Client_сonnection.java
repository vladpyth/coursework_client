import java.io.*; // Импорт пакета для ввода/вывода
import java.net.*; // Импорт пакета для работы в сети

public class Client_сonnection extends Thread {
    private Socket clientSocket = null;
    private ObjectOutputStream coos = null;
    private ObjectInputStream cois = null;
    private  String server_message=null;
    public void run() {
        try {
            System.out.println("Connecting to server...");
            clientSocket = new Socket("127.0.0.1", 2525);
            System.out.println("Connection established.");

            coos = new ObjectOutputStream(clientSocket.getOutputStream());
            cois = new ObjectInputStream(clientSocket.getInputStream());

            // Чтение сообщений от сервера в отдельном потоке
            while (true) {
                //server_message = cois.readObject().toString();
                server_message = (String) cois.readObject();
                System.out.println("~сервер~: " +server_message);
            }

        } catch (EOFException e) {
            System.err.println("Сервер закрыл соединение: " + e.getMessage());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
    }

    // Метод для отправки сообщения
    public void sendMessage(String message) throws IOException {
        if (coos != null) {
            coos.writeObject(message); // Отправка сообщения
        }
    }

    public String readMessage() {
        while (true) {
            if (server_message != null) {
                String buf = server_message;
                server_message = null; // Сбрасываем сообщение после чтения
                return buf; // Возвращаем полученное сообщение
            }

            // Опционально, добавляем паузу, чтобы избежать излишней загрузки процессора
            try {
                Thread.sleep(100); // Пауза на 100 миллисекунд
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Восстанавливаем статус прерывания
                return null; // Возвращаем null при прерывании
            }
        }
    }


    // Закрытие потоков и сокета
    private void closeConnections() {
        try {
            if (coos != null) coos.close();
            if (cois != null) cois.close();
            if (clientSocket != null) clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}