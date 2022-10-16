package kz.blazingfast.minecraft.dungeondungeonandmoredungeons.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class sha256 {
    public sha256() {
    }

    public static String hash(String msg) {
        try {
            if (msg == null) {
                msg = "";
            }

            StringBuffer sb = new StringBuffer();
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(msg.getBytes());

            for(int i = 0; i < hash.length; ++i) {
                if ((255 & hash[i]) < 16) {
                    sb.append("0" + Integer.toHexString(255 & hash[i]));
                } else {
                    sb.append(Integer.toHexString(255 & hash[i]));
                }
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException var5) {
            var5.printStackTrace();
            return null;
        }
    }
}
