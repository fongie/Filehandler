package controller;

import common.*;
import integration.LocalFileHandler;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;


/**
 * Client controller
 */
public class Controller {
   private FileServer server;
   private LocalFileHandler localFileHandler;
   private final String SERVER_IP = "localhost";
   private boolean connected;
   private String loggedInAs;

   /**
    * Constructor
    */
   public Controller() {
      connected = false;
      localFileHandler = new LocalFileHandler();
   }

   /**
    * Logs out currently logged in user, if any
    * @throws RemoteException
    */
   public void logout() throws RemoteException {
      if (loggedInAs != null) {
         server.logout(loggedInAs);
         loggedInAs = null;
      }
   }

   /**
    * Log in to server
    * @param username
    * @param password
    * @param writer
    * @return
    * @throws RemoteException
    * @throws NotBoundException
    * @throws MalformedURLException
    */
   public boolean login(String username, String password, ClientWriter writer) throws RemoteException, NotBoundException, MalformedURLException {
      if (!connected)
         establishConnection();

      boolean loggedIn = server.login(username, password, writer);

      if (loggedIn)
         loggedInAs = username;

      return loggedIn;
   }

   /**
    * Register new user
    * @param username
    * @param password
    * @return
    * @throws RemoteException
    * @throws NotBoundException
    * @throws MalformedURLException
    */
   public boolean register(String username, String password) throws RemoteException, NotBoundException, MalformedURLException {
      if (!connected)
         establishConnection();

      return server.register(username,password);
   }

   /**
    * Delete the file from server with name "remoteFile"
    * @param remoteFile
    * @throws RemoteException
    * @throws NotBoundException
    * @throws MalformedURLException
    * @throws NoSuchFileException
    * @throws AuthenticationException
    * @throws NoPermissionException
    */
   public void delete(String remoteFile) throws RemoteException, NotBoundException, MalformedURLException, NoSuchFileException, AuthenticationException, NoPermissionException {
      if (!connected)
         establishConnection();

      server.delete(remoteFile, loggedInAs);
   }

   /**
    * Download the file with name "remoteFile" from server, as "localName" on client
    * @param remoteFile
    * @param localName
    * @throws IOException
    * @throws AuthenticationException
    * @throws NotBoundException
    * @throws NoSuchFileException
    */
   public void download(String remoteFile, String localName) throws IOException, AuthenticationException, NotBoundException, NoSuchFileException {
      if (!connected)
         establishConnection();

      ReadableFile file = server.download(remoteFile,loggedInAs);
      localFileHandler.store(file, localName);
   }

   /**
    * Upload a file with name "localFile" to server, where it will be stored as "remoteName"
    * @param localFile
    * @param remoteName
    * @param writeable
    * @throws IOException
    * @throws NotBoundException
    * @throws AuthenticationException
    * @throws FilenameNotUniqueException
    * @throws NoSuchFileException
    */
   public void upload(String localFile, String remoteName, boolean writeable) throws IOException, NotBoundException, AuthenticationException, FilenameNotUniqueException, NoSuchFileException {
      if (!connected)
         establishConnection();

      FileData fileData = localFileHandler.fetchFileData(localFile, remoteName, writeable, loggedInAs);
      server.upload(fileData);
   }

   /**
    * List local files
    * @return
    * @throws IOException
    */
   public List<ReadableFile> ps() throws IOException {
      return localFileHandler.list();
   }

   /**
    * List server files
    * @return
    * @throws RemoteException
    * @throws MalformedURLException
    * @throws NotBoundException
    */
   public List<? extends ReadableFile> ls() throws RemoteException, MalformedURLException, NotBoundException {
      if (!connected)
         establishConnection();
      return server.ls();
   }

   /**
    * Quit application
    * @throws RemoteException
    */
   public void quit() throws RemoteException {
      logout();
   }

   private void establishConnection() throws RemoteException, NotBoundException, MalformedURLException {
      server = (FileServer) Naming.lookup("//" + SERVER_IP + "/fileserver");
      connected = true;
   }
}
