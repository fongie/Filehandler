package integration;


import common.FileData;
import common.FilenameNotUniqueException;
import common.NoPermissionException;
import common.NoSuchFileException;
import entities.File;
import entities.User;

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

      //TODO persist in a file on server
   }

   //returns the owner of the deleted file
   public String delete(String name, String requestedBy) throws NoSuchFileException, NoPermissionException {
      File file = fileDAO.findByName(name);
      System.out.println("Requested by: " + requestedBy);
      System.out.println("Owner : " + file.getOwnerName());
      System.out.println("Writeable : " + file.isWriteable());
      if (!file.isWriteable() && !(requestedBy == file.getOwnerName())) {
         throw new NoPermissionException();
      }
      String owner = file.getOwnerName();
      fileDAO.delete(name);
      return owner;

   }
}
