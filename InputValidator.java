package util;

import java.util.Scanner;

/**
 * Helper class for robust menu choice and user input validation.
 */
public class InputValidator {

    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Reads a non-empty string from console.
     */
    public static String readNonEmptyString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            if (input != null && !input.trim().isEmpty()) {
                return input.trim();
            }
            System.out.println("-> Error: Input cannot be empty. Please try again.");
        }
    }

    /**
     * Reads an integer within specified min and max bounds.
     */
    public static int readIntRange(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            try {
                int value = Integer.parseInt(input.trim());
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.printf("-> Error: Please enter a number between %d and %d.%n", min, max);
            } catch (NumberFormatException e) {
                System.out.println("-> Error: Invalid integer format. Please enter a valid number.");
            }
        }
    }

    /**
     * Reads student marks bounded between 0.0 and 100.0.
     */
    public static double readValidMarks(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            try {
                double marks = Double.parseDouble(input.trim());
                if (marks >= 0.0 && marks <= 100.0) {
                    return marks;
                }
                System.out.println("-> Error: Marks must be between 0.0 and 100.0.");
            } catch (NumberFormatException e) {
                System.out.println("-> Error: Invalid decimal format for marks. Please enter a valid number.");
            }
        }
    }

    /**
     * Reads a positive decimal number (e.g., road distance in meters).
     */
    public static double readPositiveDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            try {
                double val = Double.parseDouble(input.trim());
                if (val > 0) {
                    return val;
                }
                System.out.println("-> Error: Distance must be a positive number greater than 0.");
            } catch (NumberFormatException e) {
                System.out.println("-> Error: Invalid number format.");
            }
        }
    }

    public static String readStringAllowEmpty(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
}
