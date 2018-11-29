package controller;

import common.*;
import model.FileHandler;
import model.*;
import integration.FileDAO;
import integration.UserDAO;

import javax.persistence.EntityManagerFactory;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

/**
 * Controller for the server. Entry point for client connecting over RMI
 */
//unicastremoteobject makes sure EXPORT (put this in registry?) is called in constructor
public class ServerController extends UnicastRemoteObject implements FileServer {

   private UserDAO userDAO;
   private FileDAO fileDAO;
   private FileHandler fileHandler;
   private ClientHandler clientHandler;

   /**
    * Constructor
    * @param emf
    */
   public ServerController(EntityManagerFactory emf) throws RemoteException {
      userDAO = new UserDAO(emf);
      fileDAO = new FileDAO(emf);
      fileHandler = new FileHandler(fileDAO);
      clientHandler = new ClientHandler();
   }

   /**
    * Register new user
    * @param username
    * @param password
    * @return
    */
   public boolean register(String username, String password) {
      return userDAO.register(username.toLowerCase(),password);
   }

   /**
    * Log in to server
    * @param username
    * @param password
    * @param writer
    * @return
    */
   public boolean login(String username, String password, ClientWriter writer) {
      boolean success = userDAO.login(username.toLowerCase(),password);
      if (!success) {
         return false;
      }
      clientHandler.add(username, writer);
      return true;
   }

   /**
    * Logs out a user
    */
   public void logout(String username) {
      clientHandler.remove(username);
   }

   /**
    * List server files
    * @return
    */
   public List<? extends ReadableFile> ls() {
      return fileDAO.listAllFiles();
   }

   /**
    * Delete file on path "path", a request made by "requestedBy"
    * @param path
    * @param requestedBy
    * @throws AuthenticationException
    * @throws NoSuchFileException
    * @throws NoPermissionException
    */
   public void delete(String path, String requestedBy) throws AuthenticationException, NoSuchFileException, NoPermissionException {
      loginCheck(requestedBy);
      String owner = fileHandler.delete(path, requestedBy);
      clientHandler.notify(owner,requestedBy,path,"deleted");
   }

   /**
    * "requestedBy" wants to download file with name "path" from server
    * @param path
    * @param requestedBy
    * @return
    * @throws AuthenticationException
    * @throws NoSuchFileException
    */
   public ReadableFile download(String path, String requestedBy) throws AuthenticationException, NoSuchFileException {
      loginCheck(requestedBy);
      ReadableFile file =  fileHandler.download(path);
      clientHandler.notify(file.getOwnerName(),requestedBy,file.getName(),"downloaded");
      return file;
   }

   /**
    * Upload a file to server
    * @param fileData
    * @throws AuthenticationException
    * @throws FilenameNotUniqueException
    */
   public void upload(FileData fileData) throws AuthenticationException, FilenameNotUniqueException {
      loginCheck(fileData.getOwnerName());
      fileHandler.upload(fileData, userDAO.findUser(fileData.getOwnerName()));
   }

   private void loginCheck(String username) throws AuthenticationException {
      if (!clientHandler.isLoggedIn(username)) {
         throw new AuthenticationException();
      }
   }
}