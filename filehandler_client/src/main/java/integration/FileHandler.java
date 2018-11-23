package integration;

import model.WriteableFile;

public class FileHandler {

   public FileHandler() {

   }

   public FileData fetchFileData(String localFile, String remoteName) {
      return new FileData(remoteName, 10, true, "Max");

      //TODO fetch an actual file from client
   }
}
