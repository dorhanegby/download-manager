package main;

import java.util.ArrayList;

public class PartitioningService {

    private int amountOfPartitions;
    private int contentSize;

    public PartitioningService(int amountOfPartitions, int contentSize) {
        this.amountOfPartitions = amountOfPartitions;
        this.contentSize = contentSize;
    }

    public ArrayList<Range> getPartitions() {
        ArrayList<Range> partitions = new ArrayList<>();
        int iteration = 0;
        while (isMiddleIteration(iteration)) {
            partitions.add(handleMiddleRange(iteration));
            iteration++;
        }

        partitions.add(handleLastRange());
        return partitions;
    }

    private Range handleLastRange() {
        int iteration = amountOfPartitions - 1;
        int startByte = iteration  * (contentSize / amountOfPartitions);
        int endByte = contentSize - 1;

        return new Range(startByte, endByte);
    }

    private Range handleMiddleRange(int i) {
        int startByte = i * (contentSize / amountOfPartitions);
        int endByte = (i + 1) * (contentSize / amountOfPartitions) - 1;
        return new Range(startByte, endByte);
    }
    private boolean isMiddleIteration(int i) {
        return i < amountOfPartitions - 1;
    }
}
