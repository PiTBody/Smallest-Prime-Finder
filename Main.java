import com.papenko.FastSmallestChecker;
import com.papenko.FastSmallestFinder;

import java.util.logging.Level;
import java.util.logging.Logger;

static Logger LOGGER = Logger.getLogger("Main");

void main() {
    IO.println("How many threads do you want to use?");
    int threadNumber = Integer.parseInt(IO.readln());
    FastSmallestChecker fastSmallestChecker = new FastSmallestChecker();
    Thread[] threads = new Thread[threadNumber];
    for (int i = 0; i < threadNumber; i++) {
        threads[i] = new FastSmallestFinder(i, threadNumber, fastSmallestChecker);
        threads[i].start();
    }
    for (int i = 0; i < threadNumber; i++) {
        try {
            threads[i].join();
        } catch (InterruptedException e) {
            LOGGER.log(Level.SEVERE, e, e::getMessage);
        }
        try {
            fastSmallestChecker.writePrimesToFile();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e, e::getMessage);
        }
        IO.println("Finished all threads.");
    }
}
