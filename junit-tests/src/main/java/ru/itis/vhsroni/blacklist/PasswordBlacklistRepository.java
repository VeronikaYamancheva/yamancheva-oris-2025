package ru.itis.vhsroni.blacklist;

public interface PasswordBlacklistRepository {

    boolean contains(String password);
}