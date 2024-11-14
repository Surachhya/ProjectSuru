//*****************************************
//   Programmer: Surachhya Adhikari
//   CTP 150 Section#: 400
//   Final Project
//*****************************************

/**
 * Custom exception class for invalid withdrawal amounts.
 */
public class InvalidWithdrawalAmountException extends Exception {
   public InvalidWithdrawalAmountException(String message) {
      super(message);
   }
}
