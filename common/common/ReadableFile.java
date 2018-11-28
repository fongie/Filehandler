package common;

import java.io.Serializable;

public interface ReadableFile  extends Serializable {
   String getName();
   int getSize();
   boolean isWriteable();
   String getOwnerName();
}
