package health;

import java.util.Scanner;

public class HealthyLifeTracker {

    static Scanner sc = new Scanner(System.in);

    // ===== ANSI COLORS =====
    public static final String RESET = "\u001B[0m";
    public static final String CYAN_BOLD = "\u001B[96m";
    public static final String YELLOW_BOLD = "\u001B[93m";
    public static final String GREEN_BOLD = "\u001B[92m";
    public static final String RED_BOLD = "\u001B[91m";
    public static final String BLUE_BOLD = "\u001B[94m";
    public static final String PURPLE_BOLD = "\u001B[95m";

    public static void main(String[] args) {

        // Welcome Screen
        System.out.println(PURPLE_BOLD +
                "\n***********************************************\n" +
                "*          WELCOME TO HEALTHY LIFE TRACKER    *\n" +
                "*          Your Daily Health Assistant (SDG3) *\n" +
                "***********************************************\n" +
                RESET);

        int choice;
        double bmi = 0.0;
        boolean bmiCalculated = false;

        int[] waterIntake = new int[7];
        int waterIndex = 0;

        int[] calories = new int[3];
        boolean caloriesLogged = false;

        int exerciseMinutes = 0;

        do {

            showHeader();

            System.out.println(YELLOW_BOLD + "Please choose an option:" + RESET);
            System.out.println(YELLOW_BOLD + "[1] 📏 Calculate BMI" + RESET);
            System.out.println(YELLOW_BOLD + "[2] 💧 Log Daily Water Intake" + RESET);
            System.out.println(YELLOW_BOLD + "[3] 🍽️ Track Daily Calories" + RESET);
            System.out.println(YELLOW_BOLD + "[4] 🏃 Log Exercise Activity" + RESET);
            System.out.println(YELLOW_BOLD + "[5] 📊 View Health Summary" + RESET);
            System.out.println(YELLOW_BOLD + "[6] 🚪 Exit" + RESET);

            System.out.print("\nEnter your choice: ");
            choice = readInt();

            switch (choice) {

                case 1:
                    bmi = calculateBMI();
                    bmiCalculated = (bmi > 0);
                    break;

                case 2:
                    if (waterIndex < 7) {
                        System.out.print("Enter number of glasses today: ");
                        waterIntake[waterIndex++] = readInt();
                    } else {
                        System.out.println(RED_BOLD + "You have logged water for all 7 days!" + RESET);
                    }
                    break;

                case 3:
                    logCalories(calories);
                    caloriesLogged = true;
                    break;

                case 4:
                    System.out.print("Enter exercise minutes today: ");
                    exerciseMinutes += readInt();
                    break;

                case 5:
                    displaySummary(bmi, bmiCalculated, waterIntake, calories, caloriesLogged, exerciseMinutes);
                    break;

                case 6:
                    System.out.println(GREEN_BOLD + "Thank you for using HealthyLife Tracker! Stay healthy!" + RESET);
                    break;

                default:
                    System.out.println(RED_BOLD + "Invalid choice, try again." + RESET);
            }

        } while (choice != 6);

        sc.close();
    }

    // ===================== USER INTERFACE HEADER =====================
    public static void showHeader() {
        System.out.println(CYAN_BOLD +
                "\n=================================================\n" +
                "            HEALTHY LIFE TRACKER (SDG 3)\n" +
                "=================================================\n" +
                "Supports health monitoring & prevention.\n" +
                "-------------------------------------------------\n" +
                RESET);
    }
   // read integer safely from a full line
    public static int readInt() {
        while (true) {
            String line = sc.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.print(RED_BOLD + "Invalid number. Please enter again: " + RESET);
            }
        }
    }
    // read double safely from a full line
    public static double readDouble() {
        while (true) {
            String line = sc.nextLine().trim();
            try {
                return Double.parseDouble(line);
            } catch (NumberFormatException e) {
                System.out.print(RED_BOLD + "Invalid number. Please enter again: " + RESET);
            }
        }
    }
   // BMI function (accepts meters or cm)
    public static double calculateBMI() {
        System.out.print("Enter height (meters or cm): ");
        double height = readDouble();

        // if user likely entered height in cm (e.g. > 3.0), convert to meters
        if (height > 3.0) {
            height = height / 100.0;
            System.out.println(BLUE_BOLD + "Converted height to meters: " + height + RESET);
        }

        // ensures height > 0
        if (height <= 0) {
            System.out.println(RED_BOLD + "Height must be greater than 0." + RESET);
            return 0.0;
        }

        System.out.print("Enter weight in kg: ");
        double weight = readDouble();

        if (weight <= 0) {
            System.out.println(RED_BOLD + "Weight must be greater than 0." + RESET);
            return 0.0;
        }

        double bmi = weight / (height * height);

        System.out.println(BLUE_BOLD + "\n-----------------------------------------------" + RESET);
        System.out.printf("Your BMI: %.2f\n", bmi);

        if (bmi < 18.5) System.out.println(YELLOW_BOLD + "Category: Underweight" + RESET);
        else if (bmi < 24.9) System.out.println(GREEN_BOLD + "Category: Normal" + RESET);
        else if (bmi < 29.9) System.out.println(YELLOW_BOLD + "Category: Overweight" + RESET);
        else System.out.println(RED_BOLD + "Category: Obese" + RESET);

        System.out.println(BLUE_BOLD + "-----------------------------------------------" + RESET);

        return bmi;
    }

    // calorie tracking
    public static void logCalories(int[] calories) {
        String[] meals = {"Breakfast", "Lunch", "Dinner"};
        for (int i = 0; i < meals.length; i++) {
            System.out.print("Enter calories for " + meals[i] + ": ");
            calories[i] = readInt();
        }
        System.out.println(GREEN_BOLD + "Calories logged successfully!" + RESET);
    }

    // health summary
    public static void displaySummary(double bmi, boolean bmiCalculated, int[] water, int[] calories, boolean logged, int exercise) {

        System.out.println(CYAN_BOLD +
                "\n===============================================" +
                "\n               DAILY HEALTH SUMMARY            " +
                "\n===============================================\n" + RESET);

        // BMI summary
        if (bmiCalculated) {
            System.out.printf("BMI: %.2f\n", bmi);
        } else {
            System.out.println("BMI: Not calculated yet.");
        }
        System.out.println(BLUE_BOLD + "-----------------------------------------------" + RESET);

        // Water summary
        int totalWater = 0;
        for (int w : water) totalWater += w;

        System.out.println("Total Water Intake (7 days): " + totalWater + " glasses");
        if (totalWater >= 56)
            System.out.println(GREEN_BOLD + "Hydration Level: Excellent! 👍" + RESET);
        else
            System.out.println(YELLOW_BOLD + "Hydration Level: Drink at least 8 glasses/day." + RESET);

        System.out.println(BLUE_BOLD + "-----------------------------------------------" + RESET);

        // Calories summary
        if (logged) {
            int totalCal = calories[0] + calories[1] + calories[2];
            System.out.println("Total Daily Calories: " + totalCal);
        } else {
            System.out.println("Calories: Not logged yet.");
        }
        System.out.println(BLUE_BOLD + "-----------------------------------------------" + RESET);

        // Exercise summary
        System.out.println("Total Exercise Minutes: " + exercise);
        if (exercise >= 30)
            System.out.println(GREEN_BOLD + "Activity Level: Great job! Keep going!" + RESET);
        else
            System.out.println(YELLOW_BOLD + "Activity Level: Try to reach 30 min/day." + RESET);

        System.out.println(CYAN_BOLD + "===============================================\n" + RESET);
    }
}
