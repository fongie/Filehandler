package model;

import common.ClientWriter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Handles logic concerning connected clients and the notification system (where one client is notified if another accesses their files)
 */
public class ClientHandler {
   private Map<String, Client> clients;
   public static final String DOWNLOAD = "downloaded";

   public ClientHandler() {
      clients = Collections.synchronizedMap(new HashMap<String, Client>());
   }

   public void add(String username, ClientWriter writer) {
      Client client = new Client(username, writer);
      clients.put(username, client);
      client.send("Hi " + username);
   }

   public void remove(String username) {
      System.out.println("Removing " + username);
      clients.remove(username);
   }

   public void notify(String owner, String accessedBy, String file, String action) {
      try {
         clients.get(owner).send("Your file '" + file + "' was " + action + " by " + accessedBy);
      } catch (NullPointerException e) {
         return; // do nothing if owner is not logged in
      }
   }

   public void sendMessage(String username, String message) {
      clients.get(username).send(message);
   }

   public boolean isLoggedIn(String username) {
      System.out.println(username);
      System.out.println(clients.containsKey(username));
      return clients.containsKey(username);
   }
}