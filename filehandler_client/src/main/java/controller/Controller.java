package controller;

import model.ClientWriter;
import model.FileServer;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Controller {
   private FileServer server;
   private final String SERVER_IP = "localhost";
   private boolean connected;

   public Controller() {
      connected = false;
   }

   public boolean login(String username, String password, ClientWriter writer) throws RemoteException, NotBoundException, MalformedURLException {
      if (!connected)
         establishConnection();

      return server.login(username, password, writer);
   }

   public boolean register(String username, String password) throws RemoteException, NotBoundException, MalformedURLException {
      if (!connected)
         establishConnection();

      return server.register(username,password);
   }
   public void download(String remoteFile, String localName) {

   }
   public void upload(String localFile, String remoteName) {

   }

   public void ps() {

   }
   public void ls() {

   }

   public void quit() {
      //need this if client has an exported obj too
      /*
      try {
         UnicastRemoteObject.unexportObject(server, false);
      } catch (NoSuchObjectException e) {
         e.printStackTrace();
      }
      */
   }

   private void establishConnection() throws RemoteException, NotBoundException, MalformedURLException {
      server = (FileServer) Naming.lookup("//" + SERVER_IP + "/fileserver");
      connected = true;
   }
}
