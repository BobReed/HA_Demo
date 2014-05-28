package com.devreed.homeaway.Util;

import org.jasypt.util.text.BasicTextEncryptor;

import org.apache.log4j.Logger;

/**
 * Created by rreed on 5/24/2014.
 */
public class Encryption {
    protected static final Logger logger = Logger.getLogger(Encryption.class);
    private static final String salt = "ABCEASYAS123";

    public static String encrypt(String str) {
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPassword(salt);
        return textEncryptor.encrypt(str);
    }

    public static String decrypt(String str){
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPassword(salt);
        String decrypted = null;
        try{
            decrypted = textEncryptor.decrypt(str);
        } catch (Exception e){
            logger.error("Decryption not possible.  Check encrypted password for correctness");
        }

        return decrypted;
    }
}