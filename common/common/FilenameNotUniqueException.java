package common;

public class FilenameNotUniqueException  extends Exception {
   public FilenameNotUniqueException() {
      super("Filename has to be unique");
   }
}
