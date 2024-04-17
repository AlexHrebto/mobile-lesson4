package ru.mirea.khrebtovsky.cryptoloader;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class MyLoader extends AsyncTaskLoader<String> {

    private byte[] encryptedText;
    private byte[] key;

    public static final String ARG_WORD = "word";

    public MyLoader(@NonNull Context context, Bundle args) {
        super(context);
        if (args != null) {
            encryptedText = args.getByteArray(ARG_WORD);
            key = args.getByteArray("key");
        }
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Nullable
    @Override
    public String loadInBackground() {
        // Расшифровываем текст
        SecretKey originalKey = new SecretKeySpec(key, 0, key.length, "AES");
        String decryptedText = decryptMsg(encryptedText, originalKey);

        // Эмулируем длительную операцию
        SystemClock.sleep(3000);

        return decryptedText;
    }

    // Дешифрование текста
    public static String decryptMsg(byte[] cipherText, SecretKey secret) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secret);
            return new String(cipher.doFinal(cipherText));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException |
                 BadPaddingException | InvalidParameterException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }
}

