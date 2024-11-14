//*****************************************
//   Programmer: Surachhya Adhikari
//   CTP 150 Section#: 400
//   Final Project
//*****************************************

import java.io.Serializable;

/**
 * The SavingsAccount class represents a savings account that extends
 * the BankAccount class. It includes additional functionality to handle
 * account status and restrictions.
 */
public class SavingsAccount extends BankAccount implements Serializable {
   private static final double MIN_BALANCE = 25;
   private static final double WITHDRAWAL_SERVICE_CHARGE = 1.0;
   private boolean status; // active = true and inactive = false

	/**
	 * Constructs a SavingsAccount with the specified initial balance and
	 * annual interest rate.
	 *
	 * @param balance            The initial balance of the account.
	 * @param annualInterestRate The annual interest rate for the account.
	 * @exception InvalidDepositAmountException if withdraw amount is invalid.
	 */
   public SavingsAccount(double balance, double annualInterestRate) throws InvalidDepositAmountException {
      super(balance, annualInterestRate);
      status = balance >= MIN_BALANCE;
      checkStatus();
   }

	/**
	 * Constructs a SavingsAccount with the specified initial balance,
	 * annual interest rate, and monthly service charges.
	 *
	 * @param balance               The initial balance of the account.
	 * @param annualInterestRate    The annual interest rate for the account.
	 * @param monthlyServiceCharges The monthly service charges for the account.
	 * @throws InvalidDepositAmountException 
	 */
   public SavingsAccount(double balance, double annualInterestRate, double monthlyServiceCharges) throws InvalidDepositAmountException {
      super(balance, annualInterestRate, monthlyServiceCharges);
      status = balance >= MIN_BALANCE;
      checkStatus();
   }

	// Accessor for status
   public boolean isStatus() {
      return status;
   }

	// Overridden withdraw method from super class; This will also update status and warn if necessary
   @Override
   public void withdraw(double amount) throws InvalidWithdrawalAmountException {
      if (status) {
         super.withdraw(amount);
         checkStatus();
      } else {
         System.out.println( String.format("--Balance is less than $%.0f. The account is inactive.", MIN_BALANCE));
      }
   }

	// Overridden deposit method from super class; This will also update status and warn if necessary
   @Override
   public void deposit(double amount) throws InvalidDepositAmountException {
      super.deposit(amount);
      checkStatus();
   }

	// Overridden monthlyProcess method
   @Override
   public void monthlyProcess() {
   	// Check the number of withdrawals
      if (getNumOfWithdrawals() > 4) {
         double withdrawalServiceCharge = (getNumOfWithdrawals() - 4) * WITHDRAWAL_SERVICE_CHARGE;
         updateMonthlyServiceCharges(withdrawalServiceCharge);
      }
   
   	// Call the superclass method
      super.monthlyProcess();
   
   	// Check the balance after the service charge is taken
      if (super.getBalance() < MIN_BALANCE) {
         status = false; // Account becomes inactive
      }
   }

	// Private method to check and update account status
   private void checkStatus() {
      status = super.getBalance() >= MIN_BALANCE;
      if(!status){
         System.out.println( String.format("--Balance is less than $%.0f. The account is inactive.", MIN_BALANCE));
      }
   }

	/**
	 * Returns a formatted string representation of the {@code SavingsAccount}
	 * object.
	 *
	 * @return A string representation of the object.
	 */
   @Override
   public String toString() {
      return super.toString();
   }
}
