package model;

public class Command {
   private Keyword keyword;
   private String second;
   private String third;

   public Command(String input) throws IllegalArgumentException {
      parseInput(input);
   }

   private void parseInput(String input) {
      String[] parts = input.split(" ");

      if (parts.length > 3) {
         throw new IllegalArgumentException("Too many arguments given");
      }

      keyword = Keyword.valueOf(parts[0].toUpperCase());

      if (!keyword.equals(Keyword.HELP) && !keyword.equals(Keyword.LS) && !keyword.equals(Keyword.PS) && !keyword.equals(Keyword.QUIT)) {
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
