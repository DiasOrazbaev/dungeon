package kz.blazingfast.minecraft.dungeondungeonandmoredungeons.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256 {
    public SHA256() {
    }

    public static String hash(String msg) {
        try {
            if (msg == null) {
                msg = "";
            }

            StringBuilder sb = new StringBuilder();
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(msg.getBytes());

            for (byte b : hash) {
                if ((255 & b) < 16) {
                    sb.append("0").append(Integer.toHexString(255 & b));
                } else {
                    sb.append(Integer.toHexString(255 & b));
                }
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException var5) {
            var5.printStackTrace();
            return null;
        }
    }
}
