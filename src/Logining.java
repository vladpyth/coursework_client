import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Logining extends JFrame {
    private JButton registerButton = new JButton("Register");
    private JButton inButton = new JButton("login");
    private JLabel usernameLabel = new JLabel("Username:");
    private JTextField usernameField = new JTextField(20);
    private JLabel passwordLabel = new JLabel("Password:");
    private JPasswordField passwordField = new JPasswordField(20);
    Client_сonnection connector;
    Commands commands = new Commands();
    //Socket socket= new Socket();
    public Logining(Client_сonnection connector)
    {
        this.connector=connector;
        setTitle("Registration");
        setSize(500, 500);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(inButton);
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

        registerButton.setBounds(70,300,130,30);
        registerButton.setForeground(Color.black); //установка цвета переднего фона кнопки
        registerButton.setBackground(Color.BLUE); //установка цвета заднего фона кнопки

        inButton.setBounds(270,300,130,30);
        inButton.setForeground(Color.black); //установка цвета переднего фона кнопки
        inButton.setBackground(Color.BLUE); //установка цвета заднего фона кнопки

        registerButton.addActionListener(new registerButton());
        inButton.addActionListener(new infoButton());

    }
    class registerButton implements ActionListener {
        /*реализация метода, который вызывается при наступлении action-события*/
        public void actionPerformed(ActionEvent event) {
            dispose();

            if (usernameField.getText().isEmpty() && passwordField.getPassword().length == 0) {
                System.out.println("Пожалуйста, заполните все поля.");

            } else {
                String mess= usernameField.getText()+" "+passwordField.getPassword().length;
                //System.out.println("ok");
                try {
                    connector.sendMessage(mess);
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
            if (usernameField.getText().isEmpty() && passwordField.getPassword().length == 0) {
                System.out.println("Пожалуйста, заполните все поля.");

            } else {
                String mess=Commands.autorisation+" gmail "+usernameField.getText()+" "+passwordField.getText();

                try {
                    connector.sendMessage(mess);
                    String answer =connector.readMessage();


                    if ( "0".equals(answer)){
                        System.err.println("error : "+ answer);
                    }else{
                        System.out.println("answer : "+ answer);
                    }

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }


        }
    }


}

