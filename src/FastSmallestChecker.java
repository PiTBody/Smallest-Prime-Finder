package src;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;

public class FastSmallestChecker {
    List<Integer> primeStarters = Collections.synchronizedList(new ArrayList<>());

    public void check(int start, int finish, int len, int threads) {
        int mod = len % 3;
        BigInteger pow10 = BigInteger.TEN.pow(len);
        BigInteger repunit = pow10.subtract(BigInteger.ONE).divide(BigInteger.valueOf(9));
        System.out.println("Started with number: " + start);
        for (; start <= finish; start += threads) {
            if (start % (finish * 0.001) == 0) {
                System.out.println((100.0 * start) / finish + "% Done.");
            }
            if ((start + mod) % 3 != 0) {
                BigInteger n = BigInteger.valueOf(start).multiply(pow10).add(repunit);
                boolean isNPrime = n.isProbablePrime(100);
                if (isNPrime) {
                    primeStarters.add(start);
                }
            }
        }
    }

    public void writePrimesToFile(int len) throws IOException {
        List<Integer> sortedPrimeStarters = primeStarters.stream().sorted().collect(Collectors.toList());
        writeToFile(sortedPrimeStarters, "primeStarters" + len + ".txt");
    }

    private void writeToFile(List<Integer> list, String filename) throws IOException {
        List<String> lines = Collections.singletonList(list.toString());
        Path file = Paths.get(filename);
        Files.write(file, lines, UTF_8);
    }
}