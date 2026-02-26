package src;

import lombok.Value;

import java.io.IOException;
import java.math.BigInteger;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Value
public class FastSmallestChecker {
    List<Integer> primeStarters = Collections.synchronizedList(new ArrayList<>());
    int finish;
    int len;
    int mod;
    String end;
    static Logger LOGGER = Logger.getLogger("src.FastSmallestChecker");

    public FastSmallestChecker(int len, int finish) {
        this.len = len;
        this.finish = finish;
        mod = this.len % 3;
        end = "1".repeat(this.len);
    }

    public void check(int start, int threads) {
        Instant time1 = Instant.now();
        LOGGER.info("Started with number: " + start);
        for (; start <= finish; start += threads) {
            if (start % (finish * 0.01) == 0) {
                LOGGER.info(100 * start / finish + "% Done.");
            }
            if ((start + mod) % 3 != 0) {
                BigInteger N = new BigInteger(start + end);
                boolean isNPrime = N.isProbablePrime(100);
                if (isNPrime) {
                    synchronized (primeStarters) {
                        primeStarters.add(start);
                    }
                }
            }
        }
        Instant time2 = Instant.now();
        LOGGER.info(time2 + " - " + time1 + " = " + Duration.between(time1, time2));
    }

    public void writePrimesToFile() throws IOException {
        synchronized (primeStarters) {
            List<Integer> sortedPrimeStarters = primeStarters.stream().sorted().collect(Collectors.toList());
            writeToFile(sortedPrimeStarters, "primeStarters" + len + ".txt");
        }
    }

    private void writeToFile(List<Integer> list, String filename) throws IOException {
        List<String> lines = Collections.singletonList(list.toString());
        Path file = Paths.get(filename);
        Files.write(file, lines, UTF_8);
    }
}

