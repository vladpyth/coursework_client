import java.lang.reflect.Method;

public class Commands {
    static final String logining="logining";
    static final String autorisation="autorisation";
    static final String all_data_user="alldatauser";
    static final String updateUserTable="updateUserTable";


    public String logining(String[] com){

        String result=null;

        if("0".equals(com[0])){
            result= "error : Incorrect login or password entered.";
            return result;
        }else {
            result=com[2];
            return result;
        }


    }

    public String autorisation(String[] com){
        String result="1";

        if("0".equals(com[0])){
            result= "error : The entered login or email is already in use.";

        }


        return result;
    }



    public String[] splitStringIntoArray(String input) {
        // Разбиваем строку на слова по пробелам
        String[] words = input.split("\\s+");
        return words;
    }

    public String initCommand(String mess) {
        String[] com = splitStringIntoArray(mess);
        try {
            // Получаем метод по имени из первого слова
            Method method = this.getClass().getMethod(com[1], String[].class);
            // Вызываем метод

            return (String) method.invoke(this, (Object) com);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "0 client";
    }
}
