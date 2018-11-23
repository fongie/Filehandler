package controller;

import integration.FileData;
import integration.FileHandler;
import model.ClientWriter;
import model.FileServer;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Controller {
   private FileServer server;
   private FileHandler fileHandler;
   private final String SERVER_IP = "localhost";
   private boolean connected;
   private String loggedInAs;

   public Controller() {
      connected = false;
      fileHandler = new FileHandler();
   }

   public boolean login(String username, String password, ClientWriter writer) throws RemoteException, NotBoundException, MalformedURLException {
      if (!connected)
         establishConnection();

      boolean loggedIn = server.login(username, password, writer);

      if (loggedIn)
         loggedInAs = username;

      return loggedIn;
   }

   public boolean register(String username, String password) throws RemoteException, NotBoundException, MalformedURLException {
      if (!connected)
         establishConnection();

      return server.register(username,password);
   }
   public void download(String remoteFile, String localName) {

   }
   public void upload(String localFile, String remoteName) throws RemoteException {
      FileData fileData = fileHandler.fetchFileData(localFile, remoteName);
      server.upload(fileData);
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
