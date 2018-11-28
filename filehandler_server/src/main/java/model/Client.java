package model;

import common.ClientWriter;

import java.rmi.RemoteException;

/**
 * Represents a currently connected Client. Holds a remote object of the Client which can print to Client's console out.
 */
public class Client {
   private ClientWriter writer;
   private String username;

   public Client(String username, ClientWriter writer) {
      this.writer = writer;
      this.username = username;
   }

   public void send(String message) {
      try {
         writer.write(message);
      } catch (RemoteException e) {
         e.printStackTrace();
      }
   }
}
