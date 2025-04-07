import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

public class Logining extends JFrame {
    private JButton registerButton = new JButton("Register");
    private JButton infoButton = new JButton("info");
    private JLabel usernameLabel = new JLabel("Username:");
    private JTextField usernameField = new JTextField(20);
    private JLabel passwordLabel = new JLabel("Password:");
    private JPasswordField passwordField = new JPasswordField(20);
    Client_сonnection connector = new Client_сonnection();
    //Socket socket= new Socket();
    public Logining(Client_сonnection connector)
    {
        this.connector=connector;
        setTitle("Registration");
        setSize(500, 500);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(infoButton);
        add(registerButton);
        add(usernameLabel);
        add(usernameField);
        add(passwordLabel);
        add(passwordField);
        int c=2;
        usernameLabel.setBounds(100,130,200,20);
        usernameField.setBounds(250,130,130,20);




        passwordLabel.setBounds(100,170,200,20);
        passwordField.setBounds(250,170,130,20);

        registerButton.setBounds(270,300,130,30);
        registerButton.setForeground(Color.black); //установка цвета переднего фона кнопки
        registerButton.setBackground(Color.BLUE); //установка цвета заднего фона кнопки

        infoButton.setBounds(70,300,130,30);
        infoButton.setForeground(Color.black); //установка цвета переднего фона кнопки
        infoButton.setBackground(Color.BLUE); //установка цвета заднего фона кнопки

        registerButton.addActionListener(new registerButton());
        infoButton.addActionListener(new infoButton());

    }
    class registerButton implements ActionListener {
        /*реализация метода, который вызывается при наступлении action-события*/
        public void actionPerformed(ActionEvent event) {

            if (usernameField.getText().isEmpty() && passwordField.getPassword().length == 0) {
                System.out.println("Пожалуйста, заполните все поля.");

            } else {
                //System.out.println("ok");
                try {
                    connector.sendMessage("client");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                //bd.insertUser(usernameField.getText(), new String(passwordField.getPassword()));
                //bd.insertStatus(usernameField.getText(),1);
            }



        }
    }

    class infoButton implements ActionListener {
        /*реализация метода, который вызывается при наступлении action-события*/
        public void actionPerformed(ActionEvent event) {

            //bd.displayUsers();

        }
    }


}

