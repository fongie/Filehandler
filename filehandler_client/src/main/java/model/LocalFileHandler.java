package model;

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

/**
 * Handle all file operations on local computer
 */
public class LocalFileHandler {
   private Path pathToDirectory;
   private String dirName = "files";

   /**
    * Constructor. Sets file directory for files
    */
   public LocalFileHandler() {
      String root = System.getProperty("user.dir");
      Path rootDir = Paths.get(root);
      pathToDirectory = rootDir.resolve(Paths.get(dirName));
      //System.err.println(pathToDirectory.toString());
   }

   /**
    * Find a local file. Return a data transfer object with information on the file.
    * @param localFile
    * @param remoteName
    * @param writeable
    * @param owner
    * @return
    * @throws IOException
    * @throws NoSuchFileException
    */
   public FileData fetchFileData(String localFile, String remoteName, boolean writeable, String owner) throws IOException, NoSuchFileException {
      try {
         Path file = pathToDirectory.resolve(Paths.get(localFile));
         long size = Files.size(file);
         return new FileData(remoteName, Files.size(file), writeable, owner);
      } catch (java.nio.file.NoSuchFileException e) {
         throw new NoSuchFileException();
      }
   }

   /**
    * Store a file locally.
    * @param file
    * @param localName
    * @throws IOException
    */
   public void store(ReadableFile file, String localName) throws IOException {
      Path newFile = pathToDirectory.resolve(Paths.get(localName));
      Files.createFile(newFile);
   }

   /**
    * List all local files.
    * @return
    * @throws IOException
    */
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