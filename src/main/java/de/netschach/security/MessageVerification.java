package de.netschach.security;


import org.springframework.beans.factory.annotation.Value;

import jakarta.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

//@Component
class MessageVerification {

    private PublicKey publicKey;
    private PrivateKey privateKey;

    @Value("classpath:private.key")
    private String privateKeyResource;

    @Value("classpath:public.key")
    private String publicKeyResource;


    public static void _main(String[] s) throws Exception {
        // Datei
        File dir = new File("keys");

// Verzeichnis anlegen
        dir.mkdirs();

// zufaelligen Key erzeugen
        KeyPairGenerator keygen = KeyPairGenerator.getInstance("RSA");
        keygen.initialize(1024);
        KeyPair keyPair = keygen.genKeyPair();

// schluessel lesen
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();

// Public Key sichern
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKey.getEncoded());
        FileOutputStream fos = new FileOutputStream(dir.getAbsoluteFile() + "/public.key");
        fos.write(x509EncodedKeySpec.getEncoded());
        fos.close();

// Private Key sichern
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKey.getEncoded());
        fos = new FileOutputStream(dir.getAbsoluteFile() + "/private.key");
        fos.write(pkcs8EncodedKeySpec.getEncoded());
        fos.close();
    }

    @PostConstruct
    void initPublicKey() throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(this.publicKeyResource.getBytes());
        this.publicKey = keyFactory.generatePublic(publicKeySpec);
    }

    @PostConstruct
    void initPrivateKey() throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(this.privateKeyResource.getBytes());
        this.privateKey = keyFactory.generatePrivate(privateKeySpec);
    }

    String sign(String message) throws Exception {
        Signature s = Signature.getInstance("SHA256withRSA");
        s.initSign(this.privateKey);
        s.update(message.getBytes());
        return new String(Base64.getEncoder().encode(s.sign()));
    }

    boolean verify(String message, String signature) throws Exception {
        Signature s = Signature.getInstance("SHA256withRSA");
        s.initVerify(this.publicKey);
        s.update(message.getBytes());
        return s.verify(Base64.getDecoder().decode(signature));
    }
}
