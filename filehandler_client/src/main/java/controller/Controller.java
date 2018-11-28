package controller;

import integration.LocalFileHandler;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

import common.FileData;
import common.FileServer;
import common.ReadableFile;
import common.ClientWriter;
import common.AuthenticationException;


public class Controller {
   private FileServer server;
   private LocalFileHandler localFileHandler;
   private final String SERVER_IP = "localhost";
   private boolean connected;
   private String loggedInAs;

   public Controller() {
      connected = false;
      localFileHandler = new LocalFileHandler();
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
   public void upload(String localFile, String remoteName) throws RemoteException, MalformedURLException, NotBoundException {
      if (!connected)
         establishConnection();

      FileData fileData = localFileHandler.fetchFileData(localFile, remoteName);
      try {
         server.upload(fileData);
      } catch (AuthenticationException e) {
         System.out.println(e.getMessage());
      }
   }

   public void ps() {

   }
   public List<? extends ReadableFile> ls() throws RemoteException {
      return server.ls();
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
