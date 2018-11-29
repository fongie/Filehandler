package model;


import common.FileData;
import common.FilenameNotUniqueException;
import common.NoPermissionException;
import common.NoSuchFileException;
import entities.File;
import entities.User;
import integration.FileDAO;

/**
 * Handles operations concerning Files
 */
public class FileHandler {
   private FileDAO fileDAO;

   public FileHandler(FileDAO fileDAO) {
      this.fileDAO = fileDAO;
   }

   public File download(String name) throws NoSuchFileException {
      return fileDAO.findByName(name);
   }
   public void upload(FileData fileData, User owner) throws FilenameNotUniqueException {
      File file = new File(fileData.getName(), fileData.getSize(), fileData.isWriteable(), owner);
      try {
         fileDAO.findByName(fileData.getName());
         throw new FilenameNotUniqueException();
      } catch (NoSuchFileException e) {
         fileDAO.create(file);
      }
   }

   //returns the owner of the deleted file
   public String delete(String name, String requestedBy) throws NoSuchFileException, NoPermissionException {
      File file = fileDAO.findByName(name);
      if ((!file.isWriteable()) && !(requestedBy.equals(file.getOwnerName()))) {
         throw new NoPermissionException();
      }
      String owner = file.getOwnerName();
      fileDAO.delete(name);
      return owner;

   }
}
