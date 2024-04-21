package com.sebi.deliver.utils;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Hash {

    private static final Logger logger = LoggerFactory.getLogger(Hash.class);

    public static String hash(String input) {
        logger.info("Hashing input.");
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id, 32, 64);
        return argon2.hash(2,15*1024,1, input.toCharArray());
    }

    public static boolean check(String input, String hash) {
        logger.info("Checking if input corresponds to hash.");
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id, 32, 64);
        return argon2.verify(hash, input.toCharArray());
    }
}
