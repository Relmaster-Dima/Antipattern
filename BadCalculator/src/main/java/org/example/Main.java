import java.util.Scanner;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
// Антипаттерн "God Object" - один класс делает всё
public class Main{
    private static Scanner scanner = new Scanner(System.in);
    private static Properties config = new Properties(); // Антипаттерн "Soft Code"
    static int ten = 10; // Антипаттерн "Magic numbers" - только я знаю, где оно используется и что это
    static String n = "A?";   // Антипаттерн "Magic String" - раз есть Magic Numbers, то есть и стринг)))
    static {
        try {
            config.load(new FileInputStream("config.properties"));
        } catch (IOException e) {
        }
    }

    public static void main(String[] args) {
        while (true) {
            System.out.println("Введите выражение (или 'exit' для выхода):");
            String input = scanner.nextLine();

            /*if (input.equals("exit")) {
                return; // Антипаттерн "Spaghetti Code" - вообще не понятно ничего
            } */ // ТУТ Я ПОДУМАЛ, А СЛАБО БЕЗ ВЫХОДА ИЗ ЦИКЛА?

            double result = calculate(input);
            System.out.println("Результат: " + result);
        }
    }

    // Антипаттерн "Lava Flow" - мертвый код (YAGNI)
    private static void unusedMethod() {
        System.out.println("Этот метод никогда не вызывается");
    }

    public static double calculate(String expression) {
        try {
            String[] parts = expression.split(" ");
            if (parts.length != 3) {
                throw new RuntimeException("Неверный формат выражения");
            }

            double a = Double.parseDouble(parts[0]);
            String op = parts[1];
            double b = Double.parseDouble(parts[2]);

            if (op.equals("+")) {
                return a(a, b);
            } else if (op.equals("-")) {
                return s(a, b);
            } else if (op.equals("*")) {
                return m(a, b);
            } else if (op.equals("/")) {
                if(b == ten) { // Антипаттерн "Accidental complexity" + "Reinventing the square wheel" - круто придумал сделать деление на 10?
                    return ((int) a / 10 + a % 10 / 10);
                }
                else{return d(a, b);}
            } else if (op.equals("%")) {
                return m2(a, b);
            } else if (op.equals("^")) {
                return pr(a, b);
            } else {
                throw new RuntimeException(n);
            }
        } catch (Exception e) {
            return 0;
        }
    }
    // Название методов - Антипаттерн "Cryptic code" - названия методов просто ужасны
    public static double a(double a, double b) {
        return Math.addExact((int) a, (int) b);
    }
    /*public static double add(double a, double b) {  Антипаттерн "Boat anchor" - не удалил лишнее))
        return a+b;
    }*/
    public static double s(double a, double b) { // Антипаттерн "Programming by permutation" + "Blind faith" - решил задачу перебором и думаю, что b всегда больше - 10000
        int c = -10000;
        while(true)
        {
            c++;
            if((c+b) == a)break;
        }
        return c;
    }

    public static double m(double a, double b) {
        return a * b;
    }

    public static double d(double a, double b) {
        return a / b;
    }

    public static double m2(double a, double b) {
        return a % b; // Остаток от деления
    }

    public static double pr(double a, double b) {
        double result = 1;
        for (int i = 0; i < b; i++) {
            result *= a;
        }
        return result; // Антипаттерн "Reinventing the wheel" - реализация собственной логики вместо использования Math.pow(a, b)
    }
}
