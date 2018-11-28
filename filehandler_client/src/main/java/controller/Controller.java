package controller;

import common.*;
import integration.LocalFileHandler;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;


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

   public void logout() throws RemoteException {
      if (loggedInAs != null) {
         server.logout(loggedInAs);
         loggedInAs = null;
      }
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
   public void delete(String remoteFile) throws RemoteException, NotBoundException, MalformedURLException, NoSuchFileException, AuthenticationException, NoPermissionException {
      if (!connected)
         establishConnection();

      server.delete(remoteFile, loggedInAs);
   }
   public void download(String remoteFile, String localName) throws RemoteException, AuthenticationException, MalformedURLException, NotBoundException, NoSuchFileException {
      if (!connected)
         establishConnection();

      ReadableFile file = server.download(remoteFile,loggedInAs);
      localFileHandler.store(file);
   }
   public void upload(String localFile, String remoteName, boolean writeable) throws IOException, NotBoundException, AuthenticationException, FilenameNotUniqueException, NoSuchFileException {
      if (!connected)
         establishConnection();

      FileData fileData = localFileHandler.fetchFileData(localFile, remoteName, writeable, loggedInAs);
      server.upload(fileData);
   }

   public List<ReadableFile> ps() throws IOException {
      return localFileHandler.list();
   }
   public List<? extends ReadableFile> ls() throws RemoteException, MalformedURLException, NotBoundException {
      if (!connected)
         establishConnection();
      return server.ls();
   }

   public void quit() throws RemoteException {
      logout();
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
