package model;

/**
 * Represents a given user input command
 */
public class Command {
   private Keyword keyword;
   private String second;
   private String third;

   /**
    * Constructor
    * @param input
    * @throws IllegalArgumentException
    */
   public Command(String input) throws IllegalArgumentException {
      parseInput(input);
   }

   private void parseInput(String input) {
      String[] parts = input.split(" ");

      if (parts.length > 3) {
         throw new IllegalArgumentException("Too many arguments given");
      }

      keyword = Keyword.valueOf(parts[0].toUpperCase());

      if (keyword.equals(Keyword.HELP) || keyword.equals(Keyword.LOGOUT) || keyword.equals(Keyword.LS) || keyword.equals(Keyword.PS) || keyword.equals(Keyword.QUIT)) {
         if (parts.length != 1) {
            throw new IllegalArgumentException("Wrong number of arguments for this command");
         }
      }

      if (keyword.equals(Keyword.DELETE)) {
         if (parts.length != 2) {
            throw new IllegalArgumentException("Wrong number of arguments for this command");
         }
         second = parts[1].trim();
      }

      if (keyword.equals(Keyword.REGISTER) || keyword.equals(Keyword.LOGIN) ||keyword.equals(Keyword.DOWNLOAD) || keyword.equals(Keyword.UPLOAD)) {
         if (parts.length < 3) {
            throw new IllegalArgumentException("Too few arguments given");
         }
         second = parts[1].trim();
         third = parts[2].trim();
      }
   }

   public Keyword getKeyword() {
      return keyword;
   }

   public String getSecond() {
      return second;
   }

   public String getThird() {
      return third;
   }
}
