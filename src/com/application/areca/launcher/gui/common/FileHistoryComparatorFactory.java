package com.application.areca.launcher.gui.common;

import java.util.Comparator;
import java.util.GregorianCalendar;

import com.application.areca.EntryArchiveData;
import com.application.areca.metadata.manifest.Manifest;
import com.application.areca.metadata.trace.ArchiveTraceParser;
import com.myJava.file.metadata.FileMetaDataSerializationException;
import com.myJava.util.log.Logger;



/**
 * [ Action | Size | File date | Backup date ]
 */
public class FileHistoryComparatorFactory {

  /**
   * 
   * @param column Zero-based index. Cannot be negative. Range: [0, 2].
   * @return An instance of a comparator for Action, Size, File date or Backup date columns.
   * Returns a File date comparator if the column index is out of range.
   */
  public static Comparator<EntryArchiveData> forColumn(int column) {
    switch (column) {
      case  0: return new ActionColumnComparator();
      case  1: return new SizeColumnComparator();
      case  2: return new FileDateColumnComparator();
      case  3: return new BackupDateColumnComparator();
      default:
        final String message = String.format("Column (%d) out of range on toggling column sorting.", column);
        Logger.defaultLogger().error(message, "Logical View");
    }
    return new FileDateColumnComparator();
  }


  protected static class ActionColumnComparator implements Comparator<EntryArchiveData> {
    @Override
    public int compare(EntryArchiveData a1, EntryArchiveData a2) {
      if ((a1 != null) && (a2 == null)) return -1;
      if ((a1 == null) && (a2 == null)) return  0;
      if ((a1 == null) && (a2 != null)) return  1;
      
      final Manifest m1 = a1.getManifest();
      final Manifest m2 = a2.getManifest();
      if ((m1 != null) && (m2 == null)) return -1;
      if ((m1 == null) && (m2 == null)) return  0;
      if ((m1 == null) && (m2 != null)) return  1;
      
      final String d1 = m1.getDescription();
      final String d2 = m2.getDescription();
      if ((d1 != null) && (d2 == null)) return -1;
      if ((d1 == null) && (d2 == null)) return  0;
      if ((d1 == null) && (d2 != null)) return  1;
      return d1.compareTo(d2);
    }
  }


  protected static class FileDateColumnComparator implements Comparator<EntryArchiveData> {
    private long parsingError = -1;
    @Override
    public int compare(EntryArchiveData a1, EntryArchiveData a2) {
      if ((a1 != null) && (a2 == null)) return -1;
      if ((a1 == null) && (a2 == null)) return  0;
      if ((a1 == null) && (a2 != null)) return  1;

      final long lastModified1 = toMilliseconds(a1);
      final long lastModified2 = toMilliseconds(a2);
      if ((lastModified1 != parsingError) && (lastModified2 == parsingError)) return -1;
      if ((lastModified1 == parsingError) && (lastModified2 == parsingError)) return  0;
      if ((lastModified1 == parsingError) && (lastModified2 != parsingError)) return  1;
      return (lastModified1 < lastModified2) ? -1 : 1;
    }

    private long toMilliseconds(EntryArchiveData a) {
      try {
        return ArchiveTraceParser.extractFileAttributesFromTrace(a.getHash(), a.getMetadataVersion()).getLastmodified();
      } catch (FileMetaDataSerializationException e) {
        // e.printStackTrace();
        return parsingError;
      }
    }
  }


  protected static class BackupDateColumnComparator implements Comparator<EntryArchiveData> {
    @Override
    public int compare(EntryArchiveData a1, EntryArchiveData a2) {
      if ((a1 != null) && (a2 == null)) return -1;
      if ((a1 == null) && (a2 == null)) return  0;
      if ((a1 == null) && (a2 != null)) return  1;

      final Manifest m1 = a1.getManifest();
      final Manifest m2 = a2.getManifest();
      if ((m1 != null) && (m2 == null)) return -1;
      if ((m1 == null) && (m2 == null)) return  0;
      if ((m1 == null) && (m2 != null)) return  1;

      final GregorianCalendar d1 = m1.getDate();
      final GregorianCalendar d2 = m2.getDate();
      if ((d1 != null) && (d2 == null)) return -1;
      if ((d1 == null) && (d2 == null)) return  0;
      if ((d1 == null) && (d2 != null)) return  1;
      return d1.compareTo(d2);
    }
  }


  protected static class SizeColumnComparator implements Comparator<EntryArchiveData> {
    @Override
    public int compare(EntryArchiveData a1, EntryArchiveData a2) {
      if ((a1 != null) && (a2 == null)) return -1;
      if ((a1 == null) && (a2 == null)) return  0;
      if ((a1 == null) && (a2 != null)) return  1;
      return toBytes(a1) < toBytes(a2) ? -1 : 1;
    }

    private long toBytes(EntryArchiveData a) {
      return ArchiveTraceParser.extractFileSizeFromTrace(a.getHash());
    }
  }

}