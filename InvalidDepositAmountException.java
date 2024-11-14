//*****************************************
//   Programmer: Surachhya Adhikari
//   CTP 150 Section#: 400
//   Final Project
//*****************************************


/**
 * Custom exception class for invalid deposit amounts.
 */
public class InvalidDepositAmountException extends Exception {
   public InvalidDepositAmountException(String message) {
      super(message);
   }
}
