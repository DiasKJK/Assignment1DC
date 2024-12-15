public class Main {
    public static void main(String[] args) {
        int[] array = new int[100];
        for (int i = 0; i < array.length; i++) {
            array[i] = i + 1;
        }

        int numberOfThreads = 4;
        int partSize = array.length / numberOfThreads;

        SumCalculator[] calculators = new SumCalculator[numberOfThreads];
        Thread[] threads = new Thread[numberOfThreads];

        for (int i = 0; i < numberOfThreads; i++) {
            int start = i * partSize;
            int end = (i == numberOfThreads - 1) ? array.length : start + partSize;
            calculators[i] = new SumCalculator(array, start, end);
            threads[i] = new Thread(calculators[i]);
            threads[i].start();
        }

        for (int i = 0; i < threads.length; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                System.out.println("Thread " + (i + 1) + " was interrupted.");
            }
        }

        int totalSum = 0;
        for (int i = 0; i < numberOfThreads; i++) {
            System.out.println("Sum of part " + (i + 1) + ": " + calculators[i].getPartialSum());
            totalSum += calculators[i].getPartialSum();
        }
        System.out.println("Total sum: " + totalSum);
    }
}
class SumCalculator implements Runnable {
    private final int[] array;
    private final int start;
    private final int end;
    private int partialSum;

    public SumCalculator(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
        this.partialSum = 0;
    }

    @Override
    public void run() {
        for (int i = start; i < end; i++) {
            partialSum += array[i];
        }
    }

    public int getPartialSum() {
        return partialSum;
    }
}
