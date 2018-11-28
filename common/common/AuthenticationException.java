package common;

public class AuthenticationException extends Exception {

   public AuthenticationException() {
      super("Not logged in");
   }
}
