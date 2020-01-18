//package main.test;
//
//import org.junit.Test;
//
//import main.PartitioningService;
//import main.Range;
//import java.util.ArrayList;
//
//import static org.junit.Assert.*;
//
//public class PartitioningServiceTest {
//    @Test
//    public void shouldDivideToThreeEqualPartitions() {
//        PartitioningService partitioningService = new PartitioningService(3, 99);
//        ArrayList<Range> partitions = partitioningService.getPartitions();
//        assertEquals(partitions.size(), 3);
//        Range range= partitions.get(0);
//        assertEquals(range .getStartByte(), 0);
//        assertEquals(range .getEndByte(), 32);
//
//        range = partitions.get(1);
//        assertEquals(range .getStartByte(), 33);
//        assertEquals(range .getEndByte(), 65);
//
//        range = partitions.get(2);
//        assertEquals(range .getStartByte(), 66);
//        assertEquals(range .getEndByte(), 98);
//    }
//
//    @Test
//    public void shouldDivideToUnequalPartitions_LastRangeBigger() {
//        PartitioningService partitioningService = new PartitioningService(3, 100);
//        ArrayList<Range> partitions = partitioningService.getPartitions();
//        Range range= partitions.get(0);
//        assertEquals(range .getStartByte(), 0);
//        assertEquals(range .getEndByte(), 32);
//
//        range = partitions.get(1);
//        assertEquals(range .getStartByte(), 33);
//        assertEquals(range .getEndByte(), 65);
//
//        range = partitions.get(2);
//        assertEquals(range .getStartByte(), 66);
//        assertEquals(range .getEndByte(), 99);
//    }
//
//    @Test
//    public void shouldDivideToUnequalPartitions_LastRangeSmaller() {
//        PartitioningService partitioningService = new PartitioningService(3, 95);
//        ArrayList<Range> partitions = partitioningService.getPartitions();
//        Range range= partitions.get(0);
//        assertEquals(range .getStartByte(), 0);
//        assertEquals(range .getEndByte(), 30);
//
//        range = partitions.get(1);
//        assertEquals(range .getStartByte(), 31);
//        assertEquals(range .getEndByte(), 61);
//
//        range = partitions.get(2);
//        assertEquals(range .getStartByte(), 62);
//        assertEquals(range .getEndByte(), 94);
//    }
//
//}