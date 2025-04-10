import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Registrate extends JFrame {
    private JButton back = new JButton("<- back");
    private JButton registrait = new JButton("Register");
    private JLabel gmailLabel = new JLabel("gmail:");
    private JTextField gmailField = new JTextField(20);
    private JLabel usernameLabel = new JLabel("Username:");
    private JTextField usernameField = new JTextField(20);
    private JLabel passwordLabel = new JLabel("Password:");
    private JPasswordField passwordField = new JPasswordField(20);
    Client_сonnection connector;
    Commands commands = new Commands();
    //Socket socket= new Socket();

    public Registrate(Client_сonnection connector)
    {
        this.connector=connector;
        setTitle("Registration");
        setSize(500, 500);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(registrait);
        add(back);
        add(gmailField);
        add(gmailLabel);
        add(usernameLabel);
        add(usernameField);
        add(passwordLabel);
        add(passwordField);

        gmailLabel.setBounds(100,90,200,20);
        gmailField.setBounds(250,90,130,20);

        usernameLabel.setBounds(100,130,200,20);
        usernameField.setBounds(250,130,130,20);




        passwordLabel.setBounds(100,170,200,20);
        passwordField.setBounds(250,170,130,20);

        back.setBounds(70,300,130,30);
        back.setForeground(Color.black); //установка цвета переднего фона кнопки
        back.setBackground(Color.BLUE); //установка цвета заднего фона кнопки

        registrait.setBounds(270,300,130,30);
        registrait.setForeground(Color.black); //установка цвета переднего фона кнопки
        registrait.setBackground(Color.BLUE); //установка цвета заднего фона кнопки

        back.addActionListener(new back());
        registrait.addActionListener(new registrait());

    }
    class back implements ActionListener {
        /*реализация метода, который вызывается при наступлении action-события*/
        public void actionPerformed(ActionEvent event) {
            dispose();
            Logining log = new Logining(connector);
            log.setVisible(true);
            log.setResizable(false);



        }
    }

    class registrait implements ActionListener {
        /*реализация метода, который вызывается при наступлении action-события*/
        public void actionPerformed(ActionEvent event) {
            if (usernameField.getText().isEmpty() && passwordField.getPassword().length == 0) {
                System.out.println("Пожалуйста, заполните все поля.");

            } else {
                String mess=Commands.autorisation+" "+gmailField.getText()+" "+usernameField.getText()+" "+passwordField.getText();

                try {
                    connector.sendMessage(mess);
                    String answer =connector.readMessage();
                    String word = commands.initCommand(answer);

                    if ( "1".equals(word)){

                    }else{
                        System.out.println(word);
                    }

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }


        }
    }
}
