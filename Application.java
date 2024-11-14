//*****************************************
//   Programmer: Surachhya Adhikari
//   CTP 150 Section#: 400
//   Final Project
//*****************************************

/**
 * The Application class represents the main application for managing a savings account.
 * It allows the user to initialize a savings account, perform various transactions,
 * handle exceptions related to deposit and withdrawal, and save/retrieve account data to/from a binary file.
 * The application includes a menu-driven interface with options for deposit, withdrawal, viewing balance, and exit.
 *
 * Usage:
 * The main method initializes a SavingsAccount, handles user interactions, performs monthly processing,
 * and saves/retrieves account data to/from a binary file using object serialization.
 *
 * Important Note:
 * The application uses exception handling to manage invalid deposit and withdrawal amounts.
 * It expects the existence of a binary file named "savingsAccount.dat" for storing account data.
 */


import java.io.*;
import java.util.Scanner;

public class Application {
   private static final String FILE_NAME = "savingsAccount.dat";

   /**
    * The main method initializes a `SavingsAccount`, handles user interactions, performs monthly processing,
    * and saves/retrieves account data to/from a binary file using object serialization.
    *
    * @param args Command line arguments (not used).
    */
   public static void main(String[] args) {
      try {
         // A. Declare an object of the SavingsAccount class.
         SavingsAccount savingsAccount = initializeSavingsAccount();
      
         // C. Handle exceptions for InvalidWithdrawalAmount and InvalidDepositAmount
         handleExceptions(savingsAccount);
      
         // D. After the user exits the menu, call the method monthlyProcess()
         savingsAccount.monthlyProcess();
      
         // E. Save the objects to a binary file (object serialization)
         writeObj(savingsAccount);
      
         // F. Read object data from the binary file and display them on the screen (object de-serialization)
         SavingsAccount savedAccount = readObj();
         System.out.println("\nRead data from the file...");
         System.out.printf("Balance: $%.2f\n", savedAccount.getBalance());
      
      } catch (IOException | ClassNotFoundException e) {
         e.printStackTrace();
      } catch (Exception e) {
         System.out.println("Error: " + e.getMessage());
      }
   }
   
   /**
    * Initializes a `SavingsAccount` by prompting the user for initial balance,
    * interest rate, and monthly charge. Handles exceptions for invalid input
    * and ensures a valid `SavingsAccount` object is created.
    *
    * @return The initialized `SavingsAccount` object.
    */
   private static SavingsAccount initializeSavingsAccount() {
      Scanner scanner = new Scanner(System.in);
   
      SavingsAccount savingsAccount = null;
   
      double interestRate = 0.0;
      double monthlyCharge = 0.0;
   
      System.out.print("Please enter initial balance: ");
      do {
         try {
            // try to get initial balance until we got a valid amount.
            double initialBalance = getUserInputDouble(scanner);
         
            // Check if interestRate is still the initial value
            if (interestRate == 0.0) {
               System.out.print("Please enter interest rate: ");
               interestRate = getUserInputDouble(scanner);
            }
         
            // Check if monthlyCharge is still the initial value
            if (monthlyCharge == 0.0) {
               System.out.print("Please enter monthly charge: ");
               monthlyCharge = getUserInputDouble(scanner);
            }
         
            // Attempt to create a SavingsAccount
            savingsAccount = new SavingsAccount(initialBalance, interestRate, monthlyCharge);
         
         } catch (InvalidDepositAmountException e) {
            System.out.println("\nError: " + e.getMessage());
            System.out.print("Please re-enter: ");
         }
      } while (savingsAccount == null); // Repeat only for invalid entries of initial balance
   
      return savingsAccount;
   }

   /**
    * Displays the menu options for the user.
    */
   private static void displayMenu() {
      System.out.println("\nOption Menu:");
      System.out.println("1: Deposit");
      System.out.println("2: Withdraw");
      System.out.println("3: View balance");
      System.out.println("4: Exit");
   }

   /**
    * Handles user input and performs actions based on the chosen option.
    * It includes options for deposit, withdrawal, viewing balance, and exiting the program.
    *
    * @param savingsAccount The `SavingsAccount` to perform transactions on.
    */
   private static void handleExceptions(SavingsAccount savingsAccount) {
      Scanner scanner = new Scanner(System.in);
   
      int option;
      do {
      
         // B. Create a menu
         displayMenu();
      
         System.out.print("\nPlease enter your option: ");
         option = getUserInputInt(scanner);
      
         switch (option) {
            case 1:
               // Deposit
               handleDeposit(savingsAccount, scanner);
               break;
            case 2:
               // Withdraw
               handleWithdraw(savingsAccount, scanner);
               break;
            case 3:
               // View balance
               System.out.printf("--Balance: $%.2f\n", savingsAccount.getBalance());
               break;
            case 4:
               System.out.println("Saving data ...");
               // E. Save the objects to a binary file (object serialization)
               try {
                  writeObj(savingsAccount);
               } catch (IOException e) {
                  System.out.println("Error writing data to file");
                  e.printStackTrace();
               }
               System.out.println("Account data is saved!");
               break;
            default:
               System.out.println("Invalid option. Please try again.");
         }
      } while (option != 4);
   }

   /**
    * Handles the deposit operation by prompting the user for the amount to deposit.
    * Re-prompts the user in case of an invalid deposit amount.
    *
    * @param savingsAccount The `SavingsAccount` to deposit into.
    * @param scanner        The `Scanner` for user input.
    */
   private static void handleDeposit(SavingsAccount savingsAccount, Scanner scanner) {
      System.out.print("\nPlease enter amount to deposit: ");
      do {
         try {
            double depositAmount = getUserInputDouble(scanner);
            savingsAccount.deposit(depositAmount);
            break; // Exit the loop if deposit is successful
         } catch (InvalidDepositAmountException e) {
            System.out.println("Error: " + e.getMessage());
            System.out.print("Please re-enter: ");
         }
      } while (true);
   }

   /**
    * Handles the withdrawal operation by prompting the user for the amount to withdraw.
    * Re-prompts the user in case of an invalid withdrawal amount.
    *
    * @param savingsAccount The `SavingsAccount` to withdraw from.
    * @param scanner        The `Scanner` for user input.
    */
   private static void handleWithdraw(SavingsAccount savingsAccount, Scanner scanner) {
      System.out.print("\nPlease enter amount to withdraw: ");
      do {
         try {
            double withdrawAmount = getUserInputDouble(scanner);
            savingsAccount.withdraw(withdrawAmount);
            break; // Exit the loop if withdrawal is successful
         } catch (InvalidWithdrawalAmountException e) {
            System.out.println("Error: " + e.getMessage());
            System.out.print("Please re-enter: ");
         }
      } while (true);
   }

   /**
    * Gets a valid double input from the user using the provided `Scanner`.
    *
    * @param scanner The `Scanner` for user input.
    * @return The valid double input.
    */
   private static double getUserInputDouble(Scanner scanner) {
      while (!scanner.hasNextDouble()) {
         System.out.println("Invalid input. Please enter a valid number.");
         scanner.next(); // Consume the invalid input
      }
      return scanner.nextDouble();
   }

   /**
    * Gets a valid integer input from the user using the provided `Scanner`.
    *
    * @param scanner The `Scanner` for user input.
    * @return The valid integer input.
    */
   private static int getUserInputInt(Scanner scanner) {
      while (!scanner.hasNextInt()) {
         System.out.println("Invalid input. Please enter a valid number.");
         scanner.next(); // Consume the invalid input
      }
      return scanner.nextInt();
   }

   /**
    * Saves the `SavingsAccount` object to a binary file using object serialization.
    *
    * @param savingsAccount The `SavingsAccount` object to be saved.
    * @throws IOException If an I/O error occurs during serialization.
    */
   private static void writeObj(SavingsAccount savingsAccount) throws IOException {
      try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
         outputStream.writeObject(savingsAccount);
      }
   }

   /**
    * Reads object data from the binary file and returns a `SavingsAccount` object
    * using object de-serialization.
    *
    * @return The `SavingsAccount` object read from the binary file.
    * @throws IOException            If an I/O error occurs during de-serialization.
    * @throws ClassNotFoundException If the class of the serialized object cannot be found.
    */
   private static SavingsAccount readObj() throws IOException, ClassNotFoundException {
      try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
         return (SavingsAccount) inputStream.readObject();
      }
   }
}


    // Rest of the code remains unchanged...
