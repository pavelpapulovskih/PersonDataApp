import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class PersonDataApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите данные (Фамилия Имя Отчество дата_рождения номер_телефона пол):");
        String input = scanner.nextLine();

        String[] data = input.split(" ");
        if (data.length != 6) {
            System.out.println("Ошибка: Неверное количество данных. Должно быть 6 элементов.");
            return;
        }

        String lastName = data[0];
        String firstName = data[1];
        String patronymic = data[2];
        String birthDate = data[3];
        long phoneNumber;
        char gender;

        try {
            phoneNumber = Long.parseLong(data[4]);
            gender = data[5].charAt(0);
            if (gender != 'f' && gender != 'm') {
                throw new IllegalArgumentException("Ошибка: Неверный формат пола. Должно быть 'f' или 'm'.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: Неверный формат номера телефона.");
            return;
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Ошибка: Неверный формат пола.");
            return;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        }

        LocalDate birthDateObj;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            birthDateObj = LocalDate.parse(birthDate, formatter);
        } catch (DateTimeParseException e) {
            System.out.println("Ошибка: Неверный формат даты рождения. Должен быть в формате dd.mm.yyyy.");
            return;
        }

        String fileName = lastName + ".txt";
        try {
            File file = new File(fileName);
            FileWriter fileWriter = new FileWriter(file, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(lastName + firstName + patronymic + birthDateObj.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) + " " + phoneNumber + "" + gender);
            bufferedWriter.newLine();
            bufferedWriter.close();
            fileWriter.close();
            System.out.println("Данные успешно сохранены в файл " + fileName);
        } catch (IOException e) {
            System.out.println("Ошибка при записи в файл:");
            e.printStackTrace();
        }
    }
}