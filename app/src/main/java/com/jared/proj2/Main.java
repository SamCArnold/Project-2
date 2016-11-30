package com.jared.proj2;

import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.io.ByteArrayOutputStream;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Main extends AppCompatActivity {

    public final String TAG = "com.jared.proj2";

    Button encrypt;
    Button decrypt;
    TextView encryTXT;
    TextView decryTXT;
    EditText message;

    byte[] keyStart;
    byte[] key; // the key we send to the crypt functions
    byte[] msg; // the message turned to bytes that we send to the crypt functions
    byte[] encryptedData;
    byte[] decryptedData;
    KeyGenerator key_gen;
    SecureRandom secure_rand;
    SecretKey secret_key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        encrypt = (Button) findViewById(R.id.encrpyt);
        decrypt = (Button) findViewById(R.id.decrypt);
        encryTXT = (TextView) findViewById(R.id.encrypt_msg);
        decryTXT = (TextView) findViewById(R.id.decrypt_msg);
        message = (EditText) findViewById(R.id.message);

        try {
            keyStart = "this is a key".getBytes();
            key_gen = KeyGenerator.getInstance("AES");
            secure_rand = SecureRandom.getInstance("SHA1PRNG");
            secure_rand.setSeed(keyStart);
            key_gen.init(128, secure_rand); // 192 and 256 bits may not be available
            secret_key = key_gen.generateKey();
            key = secret_key.getEncoded(); // is the key we send to the functions to crypt
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "AES Key Error");
        }
    }

    /**
     *
     * @param key generated onCreate
     * @param message from the edit text
     * @return the encrypted message
     * @throws Exception
     */
    private static byte[] encrypt(byte[] key, byte[] message) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        return cipher.doFinal(message);
    }

    /**
     *
     * @param key from teh onCreate
     * @param encrypted from the encrypted text view
     * @return regular text
     * @throws Exception
     */
    private static byte[] decrypt(byte[] key, byte[] encrypted) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        return cipher.doFinal(encrypted);
    }

    //handles button clicks
    public void eventHandler(View v) {
        if (v == encrypt) {
            msg = message.getText().toString().getBytes();
            encryptedData = null;
            try {
                encryptedData = encrypt(key, msg);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "Error getting encryption");
            }
            //display the encrypted text
            encryTXT.setText("[ENCRYPTED]:\n" + Base64.encodeToString(encryptedData, Base64.DEFAULT) + "\n");
        } else if (v == decrypt) {
            decryptedData = null;
            try {
                decryptedData = decrypt(key, encryptedData);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "Error decrypting");
            }
            decryTXT.setText("[DECRYPTED]:\n" + new String(decryptedData) + "\n");
        }
    }
}