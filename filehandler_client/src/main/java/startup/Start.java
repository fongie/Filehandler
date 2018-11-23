package startup;

import view.UserInterface;

import java.rmi.RemoteException;

public class Start {
   public static void main(String[] args) {

      try {
         new UserInterface().start();
      } catch (RemoteException e) {
         e.printStackTrace();
      }
   }
}