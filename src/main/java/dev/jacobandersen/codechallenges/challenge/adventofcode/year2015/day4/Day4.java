package dev.jacobandersen.codechallenges.challenge.adventofcode.year2015.day4;

import dev.jacobandersen.codechallenges.challenge.adventofcode.Day;
import dev.jacobandersen.codechallenges.util.StringUtil;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Day4 extends Day {
    public Day4() {
        super(2015, 4, "The Ideal Stocking Stuffer");
    }

    private int findKeyAdditionGivingHashWithPrefix(String prefix) {
        final MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException("Failed to get MD5", ex);
        }

        final String key = getInputLinesNoBlanks().get(0);

        int i = 0;
        while (true) {
            md5.update(String.format("%s%d", key, i).getBytes(StandardCharsets.UTF_8));
            String hexDigest = StringUtil.toHex(md5.digest());
            if (hexDigest.startsWith(prefix)) {
                break;
            }
            md5.reset();
            i++;
        }

        return i;
    }

    public String partOne() {
        return String.valueOf(findKeyAdditionGivingHashWithPrefix("00000"));
    }

    @Override
    public String partTwo() {
        return String.valueOf(findKeyAdditionGivingHashWithPrefix("000000"));
    }
}
