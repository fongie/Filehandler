package view;

import model.Command;
import model.FileServer;
import model.Keyword;


import java.rmi.RemoteException;
import java.util.Scanner;

/**
 * The main point of user interaction, a command line view
 */
public class UserInterface {
   private FileServer fileServer;

   /**
    * Constructor for User Interface
    */
   public UserInterface() {
   }

   /**
    * Start the user interface
    */
   public void start() {
      System.out.println("Welcome to the file catalog.");
      System.out.println("Type 'help' for available commands");
      run();
   }

   /**
    * Loop to insert commands
    */
   public void run() {

      while (true) {
         Scanner in = new Scanner(System.in);
         String input = in.nextLine();
         if (input.trim().length() == 0) {
            continue;
         }
         try {
            Command command = new Command(input);
            Keyword k = command.getKeyword();
            switch (k) {
               case HELP:
                  System.out.println("NEED HELP");
                  break;
               case LOGIN:
                  System.out.println("LOGIN");
                  break;
               case REGISTER:
                  System.out.println("REGISTER");
                  fileServer.register(command.getSecond(), command.getThird());
                  break;
            }
         } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
         } catch (RemoteException e) {
            e.printStackTrace();
            System.err.println("Remote invocation of method failed.");
         }
      }
   }
}

   /*
   private class Printer implements Observer {
      public void print(Object o) {
      }
      private void printGameStatus() {
         synchronized (UserInterface.this){
            StringBuilder all = new StringBuilder();
            all.append("Your current score is ");
            all.append(status.getScore());
            all.append(".\n");
            all.append("You have ");
            all.append(status.getRemainingAttempts());
            all.append(" attempts remaining.");
            all.append("\n");

            StringBuilder word = new StringBuilder();
            for (int i = 0; i < status.getWordLength(); i++) {
               word.append("_");
            }
            for (LetterPosition lp : status.getCorrectLetters()) {
               word.setCharAt(lp.getPosition(), lp.getLetter());
            }

            for (int i = 1; i < status.getWordLength() * 2; i += 2) {
               word.insert(i, " ");
            }
            all.append(word);
            System.out.println(all.toString());
         }
      }
      private void printPrompt() {
         synchronized (UserInterface.this) {
            System.out.print(">: ");
         }
      }
   }
   */
