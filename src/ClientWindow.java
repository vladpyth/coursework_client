import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ClientWindow extends JFrame{

    private JButton registerButton = new JButton("Register");
    private JButton inButton = new JButton("login");
    private JLabel usernameLabel = new JLabel("Username:");
    private JTextField usernameField = new JTextField(20);
    private JLabel passwordLabel = new JLabel("Password:");
    private JPasswordField passwordField = new JPasswordField(20);
    private JLabel errorLabel = new JLabel();
    private String nameUser;
    Client_сonnection connector;
    Commands commands = new Commands();
    //Socket socket= new Socket();
    public ClientWindow(Client_сonnection connector, String nameUser)
    {

        this.connector=connector;
        this.nameUser=nameUser;
        setTitle("Client_window");
        setSize(1200, 700);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(inButton);
        add(registerButton);
        add(usernameLabel);
        add(usernameField);
        add(passwordLabel);
        add(passwordField);
        add(errorLabel);

        usernameLabel.setBounds(100,130,200,20);
        usernameField.setBounds(250,130,130,20);




        passwordLabel.setBounds(100,170,200,20);
        passwordField.setBounds(250,170,130,20);

        errorLabel.setForeground(Color.RED);
        errorLabel.setBounds(100,200,300,20);

        registerButton.setBounds(70,300,130,30);
        registerButton.setForeground(Color.black); //установка цвета переднего фона кнопки
        registerButton.setBackground(Color.BLUE); //установка цвета заднего фона кнопки

        inButton.setBounds(270,300,130,30);
        inButton.setForeground(Color.black); //установка цвета переднего фона кнопки
        inButton.setBackground(Color.BLUE); //установка цвета заднего фона кнопки

        registerButton.addActionListener(new registerButton());


    }
    class registerButton implements ActionListener {
        /*реализация метода, который вызывается при наступлении action-события*/
        public void actionPerformed(ActionEvent event) {
            dispose();
            Registrate reg = new Registrate(connector);
            reg.setVisible(true);
            reg.setResizable(false);



        }
    }


}
