package com.application.areca.launcher.gui.common;

import java.util.Comparator;

import com.application.areca.metadata.trace.TraceEntry;
import com.myJava.util.log.Logger;



public class ArchiveExplorerComparatorFactory {
  
  /**
   * Get an instance of a comparator for the requested column.
   * @param column Zero-based index. Cannot be negative. Range: [0, 1].
   * @return An instance of a comparator for Name or Size columns.
   * Returns a Name comparator if the column index is out of range.
   */
  public static Comparator<TraceEntry> forColumn(int column) {
    switch (column) {
      case 0: return new NameColumnComparator();
      case 1: return new SizeColumnComparator();
      default:
        final String message = String.format("Column (%d) out of range on toggling column sorting.", column);
        Logger.defaultLogger().error(message, "Archive Explorer");
    }
    return new NameColumnComparator();
  }


  protected static class NameColumnComparator implements Comparator<TraceEntry> {
    @Override
    public int compare(TraceEntry a1, TraceEntry a2) {
      if ((a1 != null) && (a2 == null)) return -1;
      if ((a1 == null) && (a2 == null)) return  0;
      if ((a1 == null) && (a2 != null)) return  1;
      
      final String d1 = a1.getKey();
      final String d2 = a2.getKey();
      if ((d1 != null) && (d2 == null)) return -1;
      if ((d1 == null) && (d2 == null)) return  0;
      if ((d1 == null) && (d2 != null)) return  1;
      return d1.compareTo(d2);
    }
  }


  protected static class SizeColumnComparator implements Comparator<TraceEntry> {
    @Override
    public int compare(TraceEntry a1, TraceEntry a2) {
      if ((a1 != null) && (a2 == null)) return -1;
      if ((a1 == null) && (a2 == null)) return  0;
      if ((a1 == null) && (a2 != null)) return  1;
      return (toBytes(a1) < toBytes(a2)) ? -1 : 1;
    }

    private long toBytes(TraceEntry a) {
      return Math.max(0, Long.parseLong(a.getData().substring(1)));
    }
  }

}