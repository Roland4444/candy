package util;

import java.nio.charset.StandardCharsets;

public class Convert__ {

    public byte[] convertToByteArray(String strToBeConverted) {
        return strToBeConverted.replaceFirst("^\uFEFF", "").getBytes(StandardCharsets.UTF_8);
    }
}
