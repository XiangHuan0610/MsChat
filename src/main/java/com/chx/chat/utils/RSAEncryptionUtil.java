package com.chx.chat.utils;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import java.io.BufferedReader;
import java.io.FileReader;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RSAEncryptionUtil {

    private static final String SMS_VERIFY_KEY = "milsun768_sms_verify_code_A5556ZZ";

    private static final String LOCAL_PUBLIC_KEY_PATH = "C:\\Users\\Chen Ziyi\\msmall-cloud\\msmall-modules\\msmall-modules-user\\src\\main\\resources\\key\\public_key.pem";

    private static final String LOCAL_PRIVAET_KEY_PATH = "C:\\Users\\Chen Ziyi\\msmall-cloud\\msmall-modules\\msmall-modules-user\\src\\main\\resources\\key\\private_key.pem";

    private static final String DOCKER_PUBLIC_KEY_PATH = "/app/public_key.pem";

    private static final String DOCKER_PRIVATE_KEY_PATH = "/app/private_key.pem";

    public static void main(String[] args) {
        String password = encryptRSA("123456");
        System.out.println(password);
        System.out.println(decryptRAS("KBSdLbkgj0oMkkPWW7HHcgCEcymLunkr3WlH8RJZ9KE+WoCFqz48zOGbfBo1M3BGSt5wJm1GknuYT/tLyjheAD2q7mcn6HTORniPLGBlPSgELrAzXIN7uBJOKL8lQVQsWIcLUfVMykgrRm/Xi1ok5dpF21yTqGG3kH51hcyxOr0="));
    }

    public static boolean symmetricDecyption(String chipher1,String chipher2){
        if (decryptRAS(chipher1).equals(decryptRAS(chipher2))){
            System.out.println("验证密码成功");
            return true;
        }
        return false;
    }

    // 加密
    public static String encryptRSA(String password) {
       try{
           String publicKeyFilePath = LOCAL_PUBLIC_KEY_PATH;

           Security.addProvider(new BouncyCastleProvider());

           String publicKeyPEM = readKeyFromFile(publicKeyFilePath);

           byte[] publicKeyEncoded = removePEMHeaders(publicKeyPEM);

           KeyFactory keyFactory = KeyFactory.getInstance("RSA");
           PublicKey publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(publicKeyEncoded));

           String encryptedPassword = encrypt(password, publicKey);

           return encryptedPassword;
       }catch (Exception e){
           e.printStackTrace();
           return password;
       }
    }

    // 解密
    public static String decryptRAS(String encryptedPassword)  {
        try{
            String privateKeyFilePath = LOCAL_PRIVAET_KEY_PATH;

            Security.addProvider(new BouncyCastleProvider());

            String privateKeyPEM = readKeyFromFile(privateKeyFilePath);


            byte[] privateKeyEncoded = removePEMHeaders(privateKeyPEM);

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateKeyEncoded));

            String decryptedPassword = decrypt(encryptedPassword, privateKey);

            return decryptedPassword;
        }catch (Exception e){
            return encryptedPassword;
        }
    }


    private static String readKeyFromFile(String filePath) throws Exception {
        StringBuilder content = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = reader.readLine()) != null) {
            content.append(line).append("\n");
        }
        reader.close();
        return content.toString();
    }

    private static byte[] removePEMHeaders(String pemKey) {
        pemKey = pemKey.replace("-----BEGIN PRIVATE KEY-----", "");
        pemKey = pemKey.replace("-----END PRIVATE KEY-----", "");
        pemKey = pemKey.replace("-----BEGIN PUBLIC KEY-----", "");
        pemKey = pemKey.replace("-----END PUBLIC KEY-----", "");
        return Base64.getMimeDecoder().decode(pemKey);
    }

    private static String encrypt(String data, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/None/PKCS1Padding", "BC");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        byte[] encryptedBytes = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    private static String decrypt(String encryptedData, PrivateKey privateKey) {
        try{
            Cipher cipher = Cipher.getInstance("RSA/None/PKCS1Padding", "BC");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            byte[] encryptedBytes = Base64.getDecoder().decode(encryptedData);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            return new String(decryptedBytes);
        }catch (Exception e){
            return "error msg";
        }
    }

    public static boolean verifySms(String pasKey) {
        try{
            return decryptRAS(pasKey).equals(SMS_VERIFY_KEY);
        }catch (Exception e){
            return false;
        }
    }
}
