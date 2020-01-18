import java.util.ArrayList;

public class PartitioningService {
    private final int amountOfPartitions;
    private final long contentSize;

    public PartitioningService(int amountOfPartitions, long contentSize) {
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
        long startByte = iteration  * (contentSize / amountOfPartitions);
        long endByte = contentSize - 1;

        return new Range(startByte, endByte);
    }

    private Range handleMiddleRange(int i) {
        long startByte = i * (contentSize / amountOfPartitions);
        long endByte = (i + 1) * (contentSize / amountOfPartitions) - 1;
        return new Range(startByte, endByte);
    }
    private boolean isMiddleIteration(int i) {
        return i < amountOfPartitions - 1;
    }
}
