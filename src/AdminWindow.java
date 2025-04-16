import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class AdminWindow extends JFrame {

    private JButton creatGroop = new JButton("creat Groop");
    private JButton modeMaster = new JButton("mode Master");
    private JLabel infoLabel = new JLabel("Status: Admin");
    private DefaultTableModel tableModel = new DefaultTableModel(new Object[]{"gmail", "login", "password", "role","status"}, 0);; // Модель таблицы
    private JTable table= new JTable(tableModel);// Таблица
    private JTextField searchField = new JTextField();; // Поле для поиска
    JScrollPane scrollPane = new JScrollPane(table);

    private JLabel errorLabel = new JLabel();
    private String nameUser;

    Client_сonnection connector;
    Commands commands = new Commands();
    //Socket socket= new Socket();
    public AdminWindow(Client_сonnection connector, String nameUser) throws IOException {

        this.connector=connector;
        this.nameUser=nameUser;
        setTitle("Admin_window");
        setSize(1200, 700);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(modeMaster);
        add(creatGroop);
        add(infoLabel);
        add(searchField);
        add(errorLabel);
        add(scrollPane);


        searchField.setBounds(650, 130, 300, 30);


        scrollPane.setBounds(450, 170, 600, 150);


        infoLabel.setBounds(650,110,200,20);


        errorLabel.setForeground(Color.RED);
        errorLabel.setBounds(100,200,300,20);

        creatGroop.setBounds(50,20,130,30);
        creatGroop.setForeground(Color.black); //установка цвета переднего фона кнопки
        creatGroop.setBackground(Color.BLUE); //установка цвета заднего фона кнопки

        modeMaster.setBounds(240,20,130,30);
        modeMaster.setForeground(Color.black); //установка цвета переднего фона кнопки
        modeMaster.setBackground(Color.BLUE); //установка цвета заднего фона кнопки

        creatGroop.addActionListener(new creatGroup());
        searchField.addActionListener(e -> filterTable());

        addData();



        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = table.rowAtPoint(evt.getPoint());
                int column = table.columnAtPoint(evt.getPoint());
                if (column ==0 || column == 1 || column == 2 ||column == 3 || column == 4) { // Если нажата колонка с кнопками5



                    int row_t=5;
                    String[] getInf= new String[row_t];
                    for(int i=0;i<row_t;i++){
                        getInf[i]=(String) tableModel.getValueAt(row, i);
                    }
                    String[] setInf=openDialog_tableUser(getInf);

                    if("1".equals(setInf[row_t])){


                        String role;
                        String status;
                        try {// нужно заменить статус и роль на числовые значения
                            switch (setInf[3]) {
                                case "user":
                                    role = "1";
                                    break; // Добавлено для выхода из switch
                                case "admin":
                                    role = "2";
                                    break; // Добавлено для выхода из switch
                                case "blocked":
                                    role = "-1";
                                    break; // Добавлено для выхода из switch
                                default:
                                    role = "1";
                                    break; // Добавлено для выхода из switch
                            }

                            switch (setInf[4]) {
                                case "offline":
                                    status = "0";
                                    break; // Добавлено для выхода из switch
                                case "online":
                                    status = "1";
                                    break; // Добавлено для выхода из switch
                                default:
                                    status = "0";
                                    break; // Добавлено для выхода из switch
                            }
                            connector.sendMessage(Commands.updateUserTable+" "+setInf[0]+" "+setInf[1]+" "+setInf[2]+" "+role+" "+status);
                            String state = connector.readMessage();
                            if("1".equals(state)){
                                JOptionPane.showMessageDialog(null, "data changed successfully!");
                                for(int i=0;i<row_t;i++){
                                    tableModel.setValueAt(setInf[i],row,i);

                                }
                            }else {
                                JOptionPane.showMessageDialog(null, "error data not saved. \n"+state);
                            }
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }


                    }

                    //System.out.println("~сервер~: " +openDialog(name));
                    //tableModel.setValueAt(openDialog(name),row,0);
                    //JOptionPane.showMessageDialog(null, "Button clicked for: " + name);

                }
            }
        });



    }


    private String[] openDialog_tableUser(String[] name) {
        String[] inputData = name; // Получаем данные из текстового поля
        DataDialog dialog = new DataDialog(this, inputData);
        dialog.setVisible(true); // Показываем диалог
        // После закрытия диалога можно обработать возвращаемые данные, если нужно
        String[] returnedData = dialog.getReturnedData();
        if (returnedData != null) {

        }
        return returnedData;
    }



    private void addData() throws IOException {
        String com=Commands.all_data_user+" admin";
        connector.sendMessage(com);
        String mess=connector.readMessage();
        String[] mess_word=commands.splitStringIntoArray(mess);
        int i=0;
        String role;
        String status;
        while (i<mess_word.length/5){
            switch (mess_word[3 + 5 * i]) {
                case "1":
                    role = "user";
                    break; // Добавлено для выхода из switch
                case "2":
                    role = "admin";
                    break; // Добавлено для выхода из switch
                case "-1":
                    role = "blocked";
                    break; // Добавлено для выхода из switch
                default:
                    role = "not defined";
                    break; // Добавлено для выхода из switch
            }

            switch (mess_word[4 + 5 * i]) {
                case "0":
                    status = "offline";
                    break; // Добавлено для выхода из switch
                case "1":
                    status = "online";
                    break; // Добавлено для выхода из switch
                default:
                    status = "not defined";
                    break; // Добавлено для выхода из switch
            }
            tableModel.addRow(new Object[]{mess_word[0+5*i],mess_word[1+5*i] ,mess_word[2+5*i] , role,status});
            i++;
        }

    }

    // Метод для фильтрации таблицы по полю поиска
    private void filterTable() {
        String searchText = searchField.getText();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);

        if (searchText.trim().length() == 0) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter(searchText));
        }
    }


    class creatGroup extends JFrame implements ActionListener {
        /*реализация метода, который вызывается при наступлении action-события*/
        public void actionPerformed(ActionEvent event) {
            CreatGroup dialog = new CreatGroup(this,nameUser);
            dialog.setVisible(true);



        }
    }
    /*
    public static void main(String[] args) throws IOException {
        Client_сonnection connector = null; // Замените на реальную реализацию
        AdminWindow window = new AdminWindow(connector, "vlad");
        window.setVisible(true);
        window.setResizable(false);
    }

     */
}


class DataDialog extends JDialog {
    private JTextField gmailField;
    private JTextField loginField;
    private JTextField passwordField;
    //private JTextField roleField;
    private JTextField statusField;
    private JButton okButton;
    private JButton applyButton;
    private String[] returnedData;
    String[] items_role = {"admin", "user", "blocked"};
    JComboBox<String> roleField;

    public DataDialog(JFrame parent, String[] data) {
        super(parent, "Data Dialog", true);
        setSize(400, 300); // Увеличиваем размер окна
        setLocationRelativeTo(parent);
        setLayout(null);

        // Инициализация полей
        gmailField = new JTextField(data[0], 15);
        loginField = new JTextField(data[1], 15);
        passwordField = new JTextField(data[2], 15);
        roleField = new JComboBox<>(items_role);
        roleField.setSelectedItem(data[3]);
        //roleField = new JTextField(data[3], 15);
        statusField = new JTextField(data[4], 15);

        // Установка позиций и размеров полей
        int fieldWidth = 200;
        int fieldHeight = 25;
        int labelWidth = 100;
        int labelHeight = 25;

        add(new JLabel("Gmail:")).setBounds(20, 20, labelWidth, labelHeight);
        add(gmailField).setBounds(120, 20, fieldWidth, fieldHeight);

        add(new JLabel("Login:")).setBounds(20, 60, labelWidth, labelHeight);
        add(loginField).setBounds(120, 60, fieldWidth, fieldHeight);

        add(new JLabel("Password:")).setBounds(20, 100, labelWidth, labelHeight);
        add(passwordField).setBounds(120, 100, fieldWidth, fieldHeight);

        add(new JLabel("Role:")).setBounds(20, 140, labelWidth, labelHeight);
        add(roleField).setBounds(120, 140, fieldWidth, fieldHeight);

        add(new JLabel("Status:")).setBounds(20, 180, labelWidth, labelHeight);
        add(statusField).setBounds(120, 180, fieldWidth, fieldHeight);

        gmailField.setEditable(false);
        statusField.setEditable(false);
        statusField.setFocusable(false);
        // Инициализация кнопок
        okButton = new JButton("OK");
        applyButton = new JButton("Apply");

        // Установка позиций и размеров кнопок
        int buttonWidth = 80;
        int buttonHeight = 25;
        okButton.setBounds(200, 220, buttonWidth, buttonHeight);
        applyButton.setBounds(290, 220, buttonWidth, buttonHeight);

        // Добавление кнопок
        add(okButton);
        add(applyButton);
        collectData("0");
        // Обработчики событий
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //collectData();
                dispose(); // Закрываем диалог
            }
        });

        applyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                applyButton.setBackground(Color.GRAY);
                collectData("1");
                // Здесь можно добавить дополнительную логику для применения данных
            }
        });
    }

    private void collectData(String status) {
        returnedData = new String[6]; // Инициализируем массив
        returnedData[0] = gmailField.getText();
        returnedData[1] = loginField.getText();
        returnedData[2] = passwordField.getText();
        returnedData[3] = (String) roleField.getSelectedItem();
        returnedData[4] = statusField.getText();
        returnedData[5] = status;
    }

    public String[] getReturnedData() {
        return returnedData; // Возвращаем данные обратно в главное окно
    }
}

class CreatGroup extends JDialog{
    private JTextField gmailField;
    private JTextField loginField;
    private JTextField nameGroup;
    private JTextArea description;
    private DefaultListModel<String> User = new DefaultListModel<>();
    private JList<String> UserList = new JList<>(User);

    private JButton delButton = new JButton("del");
    private JButton addButton = new JButton("add");
    private JButton CreatButton;
    //private JButton AddUser;

    private String[] returnedData;
    private String nameUser;
    String[] items_role = {"test", "user", "blocked"};
    JComboBox<String> roleField;

    public CreatGroup(JFrame parent, String login) {
        super(parent, "Create group", true);
        setSize(400, 550);
        setLocationRelativeTo(parent);
        setLayout(null);
        this.nameUser=login;
        // Инициализация полей

        //loginField = new JTextField("test", 15);
        loginField = new JTextField("test", 15);
        nameGroup = new JTextField("test", 15);
        roleField = new JComboBox<>(items_role);
        roleField.setSelectedItem("test");
        //roleField = new JTextField(data[3], 15);
        description = new JTextArea();
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        // Установка позиций и размеров полей
        int fieldWidth = 200;
        int fieldHeight = 25;
        int labelWidth = 100;
        int labelHeight = 25;

        // Установка режима выбора на множественный
        UserList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        // Прокручиваемая панель для списка
        JScrollPane scrollPane = new JScrollPane(UserList);

        add(scrollPane,BorderLayout.CENTER);


        add(new JLabel("User "+nameUser+" creates a group")).setBounds(20, 20, 200, labelHeight);
        //add(gmailField).setBounds(120, 20, fieldWidth, fieldHeight);

        add(new JLabel("Name group:")).setBounds(20, 60, labelWidth, labelHeight);
        add(nameGroup).setBounds(120, 60, fieldWidth, fieldHeight);
        //add(loginField).setBounds(120, 60, fieldWidth, fieldHeight);

        add(new JLabel("description:")).setBounds(20, 100, labelWidth, labelHeight);
        add(description).setBounds(120, 100, fieldWidth, 50);




        add(new JLabel("User:")).setBounds(20, 180, labelWidth, labelHeight);
        add(UserList).setBounds(120, 180, fieldWidth, 100);

        add(delButton);
        delButton.setBounds(200, 290, 55, 30);

        add(addButton);
        addButton.setBounds(270, 290, 55, 30);

        CreatButton = new JButton("Create");


        // Установка позиций и размеров кнопок
        int buttonWidth = 80;
        int buttonHeight = 25;
        CreatButton.setBounds(300, 450, buttonWidth, buttonHeight);


        // Добавление кнопок
        add(CreatButton);

        CreatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                dispose(); // Закрываем диалог
            }
        });

        delButton.addActionListener(new DelButton());
        addButton.addActionListener(new AddButton());

    }
    class DelButton implements ActionListener {
        public void actionPerformed(ActionEvent event) {
           // for (String selectedItem : whiteField.getSelectedValuesList()) {

            //}
            int selectedIndex = UserList.getSelectedIndex();
            if (selectedIndex != -1) {
                // Удаление выбранного элемента из модели
                User.remove(selectedIndex);
            } else {
                // Обработка случая, когда ничего не выбрано
                JOptionPane.showMessageDialog(null, "Please select a user to delete.");
            }

        }
    }
    class AddButton extends JFrame implements ActionListener {
        public void actionPerformed(ActionEvent event) {
           // for (String selectedItem : whiteField.getSelectedValuesList()) {

           // }
            FindUser dialog = new FindUser(this);
            dialog.setVisible(true);

            User.addElement(dialog.getReturnedData());
            User.addElement("User 2");
            User.addElement("User 3");

        }
    }


    private void collectData(String status) {
        returnedData = new String[6]; // Инициализируем массив
        returnedData[0] = gmailField.getText();
        returnedData[1] = loginField.getText();
        returnedData[2] = nameGroup.getText();
        returnedData[3] = (String) roleField.getSelectedItem();
        returnedData[4] = description.getText();
        returnedData[5] = status;
    }

    public String[] getReturnedData() {
        return returnedData; // Возвращаем данные обратно в главное окно
    }
}

class FindUser extends JDialog {
    private JTextField dataField;
    private JButton okButton;
    private String returnedData;

    public FindUser(JFrame parent) {
        super(parent, "Find user", true);
        setSize(300, 150);
        setLocationRelativeTo(parent);
        setLayout(new FlowLayout());

        dataField = new JTextField( 15); // Устанавливаем начальные данные
        okButton = new JButton("OK");

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                returnedData = dataField.getText(); // Получаем данные из текстового поля
                dispose(); // Закрываем диалог
            }
        });

        add(new JLabel("Input user:"));
        add(dataField);
        add(okButton);
    }

    public String getReturnedData() {
        return returnedData; // Возвращаем данные обратно в главное окно
    }
}
