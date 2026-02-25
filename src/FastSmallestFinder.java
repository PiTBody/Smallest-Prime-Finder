package com.papenko;

import lombok.EqualsAndHashCode;
import lombok.Value;

import java.util.logging.Level;
import java.util.logging.Logger;

@Value
@EqualsAndHashCode(callSuper = true)
public class FastSmallestFinder extends Thread {
    int start;
    int threads;
    FastSmallestChecker fastSmallestChecker;
    static Logger LOGGER = Logger.getLogger("com.papenko.FastSmallestFinder");

    @Override
    public void run() {
        try {
            fastSmallestChecker.check(start, threads);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e, e::getMessage);
        }
    }
}
