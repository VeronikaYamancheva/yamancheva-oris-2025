package ru.kpfu.itis.vhsroni.clinicsemestrovka.utils;

import lombok.experimental.UtilityClass;
import org.mindrot.jbcrypt.BCrypt;

@UtilityClass
public class HashUtil {

    public String hashPassword(String rawPassword) {
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt());
    }

    public boolean verifyPassword(String rawPassword, String hashPassword) {
        return BCrypt.checkpw(rawPassword, hashPassword);
    }
}
