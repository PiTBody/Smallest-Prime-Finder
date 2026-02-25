import java.io.IOException;
import java.util.Scanner;

public class SmallestPrimeFinder {
    static class FastSmallestFinder extends Thread {
        private int start;
        private int cores;
        private FastSmallestChecker fastSmallestChecker;

        public FastSmallestFinder(int start, int cores, FastSmallestChecker fastSmallestChecker) {
            this.start = start;
            this.cores = cores;
            this.fastSmallestChecker = fastSmallestChecker;
        }

        @Override
        public void run() {
            try {
                fastSmallestChecker.FastSmallestChecker(start, cores);
            }
            catch (Exception e) {
                System.out.println("Exception in thread: " + e.getMessage());
            }
        }
    }

    public static void main(String... args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("How many CPU threads do you want to use?");
        int cores = sc.nextInt();
        FastSmallestChecker fastSmallestChecker = new FastSmallestChecker();
        Thread[] threads = new Thread[cores];
        for (int i = 0; i < cores; i++) {
            threads[i] = new FastSmallestFinder(i, cores, fastSmallestChecker);
            threads[i].start();
        }
        for (int i = 0; i < cores; i++) {
            try {
                threads[i].join();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            fastSmallestChecker.writePrimesToFile();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Finished all threads.");
        sc.close();
    }
}

