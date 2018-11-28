package controller;

import common.*;
import integration.FileHandler;
import model.*;
import integration.FileDAO;
import integration.UserDAO;

import javax.persistence.EntityManagerFactory;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

//unicastremoteobject makes sure EXPORT (put this in registry?) is called in constructor
public class ServerController extends UnicastRemoteObject implements FileServer {

   private UserDAO userDAO;
   private FileDAO fileDAO;
   private FileHandler fileHandler;
   private ClientHandler clientHandler;

   public ServerController(EntityManagerFactory emf) throws RemoteException {
      userDAO = new UserDAO(emf);
      fileDAO = new FileDAO(emf);
      fileHandler = new FileHandler(fileDAO);
      clientHandler = new ClientHandler();
   }

   public boolean register(String username, String password) {
      return userDAO.register(username.toLowerCase(),password);
   }

   public boolean login(String username, String password, ClientWriter writer) {
      boolean success = userDAO.login(username.toLowerCase(),password);
      if (!success) {
         return false;
      }
      clientHandler.add(username, writer);
      return true;
   }

   public List<? extends ReadableFile> ls() {
      return fileDAO.listAllFiles();
   }

   public void delete(String path, String requestedBy) throws AuthenticationException, NoSuchFileException {
      loginCheck(requestedBy);
      String owner = fileHandler.delete(path);
      clientHandler.notify(owner,requestedBy,path,"deleted");
   }
   public ReadableFile download(String path, String requestedBy) throws AuthenticationException, NoSuchFileException {
      loginCheck(requestedBy);
      ReadableFile file =  fileHandler.download(path);
      clientHandler.notify(file.getOwnerName(),requestedBy,file.getName(),"downloaded");
      return file;
   }

   public void upload(FileData fileData) throws AuthenticationException, FilenameNotUniqueException {
      loginCheck(fileData.getOwnerName());
      System.out.println("UPLOADING");
      fileHandler.upload(fileData, userDAO.findUser(fileData.getOwnerName()));
   }

   private void loginCheck(String username) throws AuthenticationException {
      if (!clientHandler.isLoggedIn(username)) {
         throw new AuthenticationException();
      }
   }
}