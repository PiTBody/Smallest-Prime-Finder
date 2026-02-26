import src.FastSmallestChecker;

import java.util.logging.Level;
import java.util.logging.Logger;

static Logger LOGGER = Logger.getLogger("Main");

void main() {
    int threadNumber = Integer.parseInt(IO.readln("How many threads do you want to use? "));
    int finishNumber = Integer.parseInt(IO.readln("How many numbers do you want to check? "));
    int lenNumber = Integer.parseInt(IO.readln("How many repunits at the end do you want to have? "));
    Instant time1 = Instant.now();
    Thread[] threads = new Thread[threadNumber];
    FastSmallestChecker fastSmallestChecker = new FastSmallestChecker();
    for (int i = 0; i < threadNumber; i++) {
        int finalI = i;
        threads[i] = new Thread(() -> fastSmallestChecker.check(finalI, finishNumber, lenNumber, threadNumber));
        threads[i].start();
    }
    for (int i = 0; i < threadNumber; i++) {
        try {
            threads[i].join();
        } catch (InterruptedException e) {
            LOGGER.log(Level.SEVERE, e, e::getMessage);
        }
        try {
            fastSmallestChecker.writePrimesToFile(lenNumber);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e, e::getMessage);
        }
    }
    IO.println("Finished all threads.");
    Instant time2 = Instant.now();
    LOGGER.info(String.valueOf(Duration.between(time1, time2)));
}
