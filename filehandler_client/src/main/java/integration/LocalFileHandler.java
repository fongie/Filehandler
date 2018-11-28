package integration;

import common.FileData;
import common.NoSuchFileException;
import common.ReadableFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class LocalFileHandler {
   private Path pathToDirectory;
   private String dirName = "files";

   public LocalFileHandler() {
      String root = System.getProperty("user.dir");
      Path rootDir = Paths.get(root);
      pathToDirectory = rootDir.resolve(Paths.get(dirName));
      //System.err.println(pathToDirectory.toString());
   }

   public FileData fetchFileData(String localFile, String remoteName, boolean writeable, String owner) throws IOException, NoSuchFileException {
      try {
         Path file = pathToDirectory.resolve(Paths.get(localFile));
         long size = Files.size(file);
         return new FileData(remoteName, Files.size(file), writeable, owner);
      } catch (java.nio.file.NoSuchFileException e) {
         throw new NoSuchFileException();
      }
   }

   public void store(ReadableFile file) {

   }

   public List<ReadableFile> list() throws IOException {
      List<ReadableFile> list = new ArrayList<>();
      Stream<Path> stream = Files.list(pathToDirectory);
      stream.forEach(f -> {
         FileData file = null;
         try {
            file = new FileData(f.getFileName().toString(),Files.size(f),true, null);
         } catch (IOException e) {
            e.printStackTrace();
         }
         list.add(file);
      });
      return list;
   }
}