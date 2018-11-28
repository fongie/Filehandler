package integration;

import common.FileData;

public class LocalFileHandler {

   public LocalFileHandler() {

   }

   public FileData fetchFileData(String localFile, String remoteName) {
      return new FileData(remoteName, 10, true, "Max");

      //TODO fetch an actual file from client
   }
}
