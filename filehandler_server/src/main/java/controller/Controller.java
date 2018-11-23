package controller;

import entities.ReadableFile;
import integration.FileDAO;
import integration.UserDAO;
import model.ClientHandler;
import model.ClientWriter;
import model.FileHandler;
import model.FileServer;

import javax.persistence.EntityManagerFactory;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

//unicastremoteobject makes sure EXPORT (put this in registry?) is called in constructor
public class Controller extends UnicastRemoteObject implements FileServer {

   private UserDAO userDAO;
   private FileDAO fileDAO;
   private FileHandler fileHandler;
   private ClientWriter writer;
   private ClientHandler clientHandler;

   public Controller(EntityManagerFactory emf) throws RemoteException {
      userDAO = new UserDAO(emf);
      fileDAO = new FileDAO(emf);
      fileHandler = new FileHandler(fileDAO);
      clientHandler = new ClientHandler();
   }

   public boolean register(String username, String password) {
      return userDAO.register(username,password);
   }

   public boolean login(String username, String password, ClientWriter writer) {
      boolean success = userDAO.login(username,password);
      if (!success) {
         return false;
      }
      clientHandler.add(username, writer);
      return true;
   }

   public List<ReadableFile> list() {
      return fileDAO.listAllFiles();
   }

   public void download() {
      fileHandler.download();

   }

   public void upload() {
      fileHandler.upload();
   }

}
