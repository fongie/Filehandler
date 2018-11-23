package model;

public class ClientHandler {
   private Client client;

   public ClientHandler() {

   }

   public void add(String username, ClientWriter writer) {
      client = new Client(username, writer);
      client.send("Hi " + username);
   }
}