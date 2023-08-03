import java.io.*;
import java.nio.file.FileSystemException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
    public static void main(String[] args) throws Exception {
        try {
            addPerson();
            System.out.println("successfully added");
        } catch (FileSystemException e) {
            System.out.println(e.getMessage());
        }

    }

    public static void addPerson() throws Exception {
        System.out.println("Type your last, first and patronymic names, date of birth(dd.mm.yyyy), phone number(without any dividers) and gender(m or f). Use space between the items");
        String userText;
        try (BufferedReader bf = new BufferedReader(new InputStreamReader(System.in))) {
            userText = bf.readLine();
        } catch (IOException e) {
            throw new Exception("Console error");
        }

        String[] arr = userText.split(" ");
        if (arr.length != 6) {
            throw new Exception("Something is missing. Check all the items for typing");
        }

        String lastName = arr[0];
        String name = arr[1];
        String patronymicName = arr[2];

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.mm.yyyy");
        Date birthDate;
        try {
            birthDate = dateFormat.parse(arr[3]);
        } catch (ParseException e) {
            throw new ParseException("Incorrect date format. Type dd.mm.yyyy", e.getErrorOffset());
        }

        int phoneNumber;
        try {
            phoneNumber = Integer.parseInt(arr[4]);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("incorrect phone number format. Type only numbers without any dividers");
        }

        String gender = arr[5];
        if (!gender.toLowerCase().equals("m") && !gender.toLowerCase().equals("f")) {
            throw new RuntimeException("Incorrect gender. Type f or m");
        }

        String fileName = lastName + ".txt";
        File file = new File(fileName);
        try (FileWriter fw = new FileWriter(file);
             BufferedWriter bf = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bf)) {
            out.print(String.format("%s %s %s %s %s %s", lastName, name, patronymicName, dateFormat.format(birthDate), phoneNumber, gender));
            System.out.println("Success");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
