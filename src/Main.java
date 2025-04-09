
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;



public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

        Client_сonnection connector = new Client_сonnection();
        connector.start(); // Запуск потока
        Logining log = new Logining(connector);
        log.setVisible(true);
        log.setResizable(false);


    }
}


// Пример отправки сообщения
// Задержка, чтобы убедиться, что соединение установлено
        /*
        try {
            Thread.sleep(1000); // Ожидание 1 секунду
            connector.sendMessage("ggrgr"); // Отправка сообщения
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Thread.sleep(1000);
        System.out.println("~сервер~: " +connector.server_message);
        while (true){
            if(connector.server_message!=null){
                System.out.println("~сервер~: " +connector.server_message);
                connector.server_message=null;
                break;
            }
        }
        if(connector.server_message==null){
            System.out.println("~ ");


        }
*/
//coos.writeObject("09");

//connector.write();