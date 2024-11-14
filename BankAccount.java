//*****************************************
//   Programmer: Surachhya Adhikari
//   CTP 150 Section#: 400
//   Final Project
//*****************************************

import java.io.Serializable;

/**
 * This abstract class represents a generic bank account with basic
 * functionality. It includes features such as balance, number of deposits,
 * number of withdrawals, annual interest rate, and monthly service charges.
 * This class can be extended to create specific types of bank accounts.
 */
public abstract class BankAccount implements Serializable {
/** Private members
*/
   private double balance;
   private int numOfDeposits;
   private int numOfWithdrawals;
   private double annualInterestRate;
   private double monthlyServiceCharges;

	/**
	 * Constructs a BankAccount with the specified initial balance and
	 * annual interest rate.
	 *
	 * @param balance            The initial balance of the account.
	 * @param annualInterestRate The annual interest rate for the account.
	 * @exception InvalidDepositAmountException If the initial deposit amount is invalid.
	 */
   public BankAccount(double balance, double annualInterestRate) throws InvalidDepositAmountException {
      if (balance <= 0 || balance > 10000) {
         throw new InvalidDepositAmountException("amount to deposit should be > 0 and < 10,000.");
      }
      this.balance = balance;
      this.annualInterestRate = annualInterestRate;
   }


	/**
	 * Constructs a BankAccount with the specified initial balance, annual
	 * interest rate, and monthly service charges.
	 *
	 * @param balance               The initial balance of the account.
	 * @param annualInterestRate    The annual interest rate for the account.
	 * @param monthlyServiceCharges The monthly service charges for the account.
	 * @exception InvalidDepositAmountException for Invalid deposit amount
	 */
   public BankAccount(double balance, double annualInterestRate, double monthlyServiceCharges) throws InvalidDepositAmountException {
      this(balance, annualInterestRate);
      this.monthlyServiceCharges = monthlyServiceCharges;
   }

	// Accessors and Mutators...

	/**
	 * Retrieves the balance of the account.
	 *
	 * @return The current balance of the account.
	 */
   public double getBalance() {
      return balance;
   }

	// Other methods...

	/**
	 * Deposits the specified amount into the account.
	 *
	 * @param amount The amount to be deposited.
	 * @exception InvalidDepositAmountException for Invalid deposit amount
	 */
   public void deposit(double amount) throws InvalidDepositAmountException {
      if (amount <= 0 || amount > 10000) {
         throw new InvalidDepositAmountException("amount to deposit should be > 0 and < 10,000.");
      }
      balance += amount;
      numOfDeposits++;
   }

	/**
	 * Withdraws the specified amount from the account.
	 *
	 * @param amount The amount to be withdrawn.
	 * @throws InvalidWithdrawalAmountException for Invalid withdrawal amount
	 */
   public void withdraw(double amount) throws InvalidWithdrawalAmountException {
      if (amount <= 0 || amount > balance || amount > 10000) {
         throw new InvalidWithdrawalAmountException("there is no enough money to be withdraw.");
      }
      balance -= amount;
      numOfWithdrawals++;
   }

	/**
	 * Calculates and updates the balance by adding monthly interest based on the
	 * annual interest rate.
	 */
   public void calcInterest() {
      double monthlyInterestRate = annualInterestRate / 12;
      double monthlyInterest = balance * monthlyInterestRate;
      balance += monthlyInterest;
   }

	/**
	 * Processes the monthly activities for the account, including subtracting
	 * monthly service charges, calculating monthly interest, and resetting counts.
	 */
   public void monthlyProcess() {
      balance -= monthlyServiceCharges;
      calcInterest();
      numOfDeposits = 0;
      numOfWithdrawals = 0;
      monthlyServiceCharges = 0;
   }

	/**
	 * Returns the number of withdrawals for the month.
	 *
	 * @return The number of withdrawals made in the current month.
	 */
   protected int getNumOfWithdrawals() {
      return numOfWithdrawals;
   }

	/**
	 * Returns the monthly service charges for the account.
	 *
	 * @return The monthly service charges for the account.
	 */
   protected double getMonthlyServiceCharges() {
      return monthlyServiceCharges;
   }

	/**
	 * Updates the monthly service charges for the account.
	 *
	 * @param additionalCharges The additional charges to be added to the monthly
	 *                          service charges.
	 */
   protected void updateMonthlyServiceCharges(double additionalCharges) {
      monthlyServiceCharges += additionalCharges;
   }

	/**
	 * Returns a formatted string representation of the {@code BankAccount} object.
	 *
	 * @return A string representation of the object.
	 */
   @Override
   public String toString() {
      return String.format("Balance: $%.2f\nNumber of Deposit: %s \nNumber of withdrawls: %s", balance, numOfDeposits, numOfWithdrawals);
   }
}
