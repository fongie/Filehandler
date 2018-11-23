package model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ClientHandler {
   private Map<String, Client> clients;

   public ClientHandler() {
      clients = Collections.synchronizedMap(new HashMap<String, Client>());
   }

   public void add(String username, ClientWriter writer) {
      Client client = new Client(username, writer);
      clients.put(username, client);
      client.send("Hi " + username);
   }

   public void notify(String owner, String accessedBy, String file, String action) {
      clients.get(owner).send("Your file '" + file + "' was " + action + " by " + accessedBy);
   }
}