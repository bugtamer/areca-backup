package com.application.areca.launcher.gui.common;

import java.io.File;
import java.util.Comparator;

import com.myJava.file.FileSystemManager;
import com.myJava.util.log.Logger;



public class PhysicalViewComparatorFactory {
  
  /**
   * Get an instance of a comparator for the requested column.
   * @param column Zero-based index. Cannot be negative. Range: [0, 2].
   * @return An instance of a comparator for Archive, Date or Size columns.
   * Returns a Date comparator if the column index is out of range.
   */
  public static Comparator<File> forColumn(int column) {
    switch (column) {
      case 0: return new ArchiveColumnComparator();
      case 1: return new DateColumnComparator();
      case 2: return new SizeColumnComparator();
      default:
        final String message = String.format("Column (%d) out of range on toggling column sorting.", column);
        Logger.defaultLogger().error(message, "Physical View");
    }
    return new DateColumnComparator();
  }


  /** @see FileComparator */
  protected static class ArchiveColumnComparator implements Comparator<File> {
    @Override
    public int compare(File f1, File f2) {
      if ((f1 != null) && (f2 == null)) return -1;
      if ((f1 == null) && (f2 == null)) return  0;
      if ((f1 == null) && (f2 != null)) return  1;
      String p1 = FileSystemManager.getAbsolutePath(f1);
      String p2 = FileSystemManager.getAbsolutePath(f2);
      return p1.compareTo(p2);
    }
  }


  protected static class DateColumnComparator implements Comparator<File> {
    @Override
    public int compare(File f1, File f2) {
      if ((f1 != null) && (f2 == null)) return -1;
      if ((f1 == null) && (f2 == null)) return  0;
      if ((f1 == null) && (f2 != null)) return  1;
      return (f1.lastModified() < f2.lastModified()) ? -1 : 1;
    }
  }


  protected static class SizeColumnComparator implements Comparator<File> {
    @Override
    public int compare(File f1, File f2) {
      if ((f1 != null) && (f2 == null)) return -1;
      if ((f1 == null) && (f2 == null)) return  0;
      if ((f1 == null) && (f2 != null)) return  1;
      return (f1.length() < f2.length()) ? -1 : 1;
    }
  }

}