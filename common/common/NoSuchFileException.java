package common;

public class NoSuchFileException extends Exception {
   public NoSuchFileException() {
      super("Cannot find the file requested");
   }
}
