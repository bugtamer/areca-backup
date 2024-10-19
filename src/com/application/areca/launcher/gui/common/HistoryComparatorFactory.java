package com.application.areca.launcher.gui.common;

import java.util.Comparator;

import com.myJava.util.history.HistoryEntry;
import com.myJava.util.log.Logger;



public class HistoryComparatorFactory {

  /**
   * 
   * @param column Zero-based index. Cannot be negative. Range: [0, 2].
   * @return An instance of a comparator for Name, Date or Type columns.
   * Returns a Date comparator if the column index is out of range.
   */
  public static Comparator<HistoryEntry> forColumn(int column) {
    switch (column) {
      case 0: return new NameColumnComparator();
      case 1: return new DateColumnComparator();
      case 2: return new TypeColumnComparator();
      default:
        final String message = String.format("Column (%d) out of range on toggling column sorting.", column);
        Logger.defaultLogger().error(message, "History View");
    }
    return new DateColumnComparator();
  }


  protected static class NameColumnComparator implements Comparator<HistoryEntry> {
    @Override
    public int compare(HistoryEntry h1, HistoryEntry h2) {
      if ((h1 != null) && (h2 == null)) return -1;
      if ((h1 == null) && (h2 == null)) return  0;
      if ((h1 == null) && (h2 != null)) return  1;
      
      final String s1 = h1.getDescription();
      final String s2 = h2.getDescription();
      if ((s1 != null) && (s2 == null)) return -1;
      if ((s1 == null) && (s2 == null)) return  0;
      if ((s1 == null) && (s2 != null)) return  1;
      return s1.compareTo(s2);
    }
  }


  protected static class DateColumnComparator implements Comparator<HistoryEntry> {
    @Override
    public int compare(HistoryEntry h1, HistoryEntry h2) {
      if ((h1 != null) && (h2 == null)) return -1;
      if ((h1 == null) && (h2 == null)) return  0;
      if ((h1 == null) && (h2 != null)) return  1;
      return h1.getDate().compareTo(h2.getDate());
    }
  }


  protected static class TypeColumnComparator implements Comparator<HistoryEntry> {
    @Override
    public int compare(HistoryEntry h1, HistoryEntry h2) {
      if ((h1 != null) && (h2 == null)) return -1;
      if ((h1 == null) && (h2 == null)) return  0;
      if ((h1 == null) && (h2 != null)) return  1;
      return (h1.getType() < h2.getType()) ? -1 : 1;
    }
  }

}