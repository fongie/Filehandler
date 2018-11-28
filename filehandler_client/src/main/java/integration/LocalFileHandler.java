package integration;

import common.FileData;
import common.ReadableFile;

public class LocalFileHandler {

   public LocalFileHandler() {

   }

   public FileData fetchFileData(String localFile, String remoteName, String owner) {
      return new FileData(remoteName, 10, true, owner);

      //TODO fetch an actual file from client
   }

   public void store(ReadableFile file) {

   }
}