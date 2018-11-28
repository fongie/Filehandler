package common;

import java.io.Serializable;

public interface ReadableFile  extends Serializable {
   String getName();
   long getSize();
   boolean isWriteable();
   String getOwnerName();
}
