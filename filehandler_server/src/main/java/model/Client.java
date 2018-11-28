package model;

import common.ClientWriter;

import java.rmi.RemoteException;

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
