package common;

public class NoPermissionException extends Exception {

   public NoPermissionException() {
      super("You do not have permission to change or delete this file");
   }
}
