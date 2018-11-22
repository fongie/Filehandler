package controller;

import entities.ReadableFile;
import integration.FileDAO;
import integration.UserDAO;
import model.FileHandler;

import javax.persistence.EntityManagerFactory;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class Controller extends UnicastRemoteObject implements FileServer {
   //TODO copy fileserver over from client

   private UserDAO userDAO;
   private FileDAO fileDAO;
   private FileHandler fileHandler;

   public Controller(EntityManagerFactory emf) {
      userDAO = new UserDAO(emf);
      fileDAO = new FileDAO(emf);
      fileHandler = new FileHandler(fileDAO);
   }

   public boolean register(String username, String password) {
      return userDAO.register(username,password);
   }

   public boolean login(String username, String password) {
      return userDAO.login(username,password);
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
