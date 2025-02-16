package com.memefest.Security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.LocatorAdapter;
import io.jsonwebtoken.ProtectedHeader;
import io.jsonwebtoken.security.EcPrivateJwk;
import io.jsonwebtoken.security.JwkThumbprint;
import io.jsonwebtoken.security.Jwks;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.Security;
import java.security.UnrecoverableEntryException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;

public class CustomKeyLocator extends LocatorAdapter<Key> {
  public final String keyAlias = "jinice";
  
  public static final String subjectDn = "CN=Jinice";
  
  public static final String keyStorePass = "changeit";
  
  public static final String pkcs12StorePass = "changeit";
  
  public static final String jksLocation = "file:/J:/keystore.jks";
  
  public static KeyStore jkStore;
  
  static {
    try {
      Properties properties = new Properties();
      properties.setProperty("keyStore", "file:/J:/keystore.jks");
      jkStore = KeyStore.getInstance("JKS");
      FileInputStream fis = new FileInputStream(new File(new URI("file:/J:/keystore.jks")));
      jkStore.load(fis, "changeit".toCharArray());
    } catch (KeyStoreException ex) {
      ex.printStackTrace();
    } catch (NoSuchAlgorithmException ex) {
      ex.printStackTrace();
    } catch (CertificateException ex) {
      ex.printStackTrace();
    } catch (URISyntaxException ex) {
      ex.printStackTrace();
    } catch (IOException ex) {
      ex.printStackTrace();
    } 
  }
  
  public Key locate(ProtectedHeader header) {
    String keyId = header.getKeyId();
    KeyPair key = null;
    try {
      key = lookupKey();
    } catch (KeyStoreException ex) {
      ex.printStackTrace();
    } catch (NoSuchAlgorithmException ex) {
      ex.printStackTrace();
    } catch (UnrecoverableEntryException ex) {
      ex.printStackTrace();
    } catch (IOException ex) {
      ex.printStackTrace();
    } catch (CertificateException ex) {
      ex.printStackTrace();
    } catch (OperatorCreationException ex) {
      ex.printStackTrace();
    } 
    return key.getPublic();
  }
  
  public KeyPair lookupKey() throws KeyStoreException, NoSuchAlgorithmException, UnrecoverableEntryException, IOException, CertificateException, OperatorCreationException {
    KeyPair keyPair = loadFromJKS();
    return keyPair;
  }
  
  public static KeyPair createNewKey() {
    KeyPair pair = (KeyPair)Jwts.SIG.ES512.keyPair().build();
    return pair;
  }
  
  public void storeKey(KeyPair pair) throws KeyStoreException, NoSuchAlgorithmException, UnrecoverableEntryException, IOException, CertificateException, OperatorCreationException {
    Certificate cert = selfSign(pair);
    jkStore.setKeyEntry("jinice", pair.getPrivate(), "changeit".toCharArray(), new Certificate[] { cert });
  }
  
  public static Certificate selfSign(KeyPair pair) throws OperatorCreationException, CertificateException {
    BouncyCastleProvider bouncyCastleProvider = new BouncyCastleProvider();
    Security.addProvider((Provider)bouncyCastleProvider);
    long now = System.currentTimeMillis();
    Date startDate = new Date(now);
    X500Name dnName = new X500Name("CN=Jinice");
    BigInteger certSerialNumber = new BigInteger(Long.toString(now));
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(startDate);
    calendar.add(1, 1);
    Date endDate = calendar.getTime();
    String signatureAlgorithm = "ES512";
    SubjectPublicKeyInfo subjectPublicKeyInfo = SubjectPublicKeyInfo.getInstance(pair.getPublic().getEncoded());
    X509v3CertificateBuilder certificateBuilder = new X509v3CertificateBuilder(dnName, certSerialNumber, startDate, endDate, dnName, subjectPublicKeyInfo);
    ContentSigner contentSigner = (new JcaContentSignerBuilder(signatureAlgorithm)).setProvider((Provider)bouncyCastleProvider).build(pair.getPrivate());
    X509CertificateHolder certificateHolder = certificateBuilder.build(contentSigner);
    Certificate selfSignedCertificate = (new JcaX509CertificateConverter()).getCertificate(certificateHolder);
    return selfSignedCertificate;
  }
  
  public static String getThumbPrint(KeyPair pair) {
    EcPrivateJwk ecPrivateJwk = (EcPrivateJwk)Jwks.builder().ecKeyPair(pair).build();
    JwkThumbprint thumbPrint = ecPrivateJwk.thumbprint();
    return thumbPrint.toString();
  }
  
  public static KeyPair loadFromPkcs12(String filename, char[] password) throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException, UnrecoverableEntryException {
    KeyStore pkcs12Store = KeyStore.getInstance("PKCS12");
    FileInputStream fis = new FileInputStream(filename);
    try {
      pkcs12Store.load(fis, password);
      fis.close();
    } catch (Throwable throwable) {
      try {
        fis.close();
      } catch (Throwable throwable1) {
        throwable.addSuppressed(throwable1);
      } 
      throw throwable;
    } 
    KeyStore.ProtectionParameter param = new KeyStore.PasswordProtection(password);
    KeyStore.Entry entry = pkcs12Store.getEntry("jinice", param);
    if (!(entry instanceof KeyStore.PrivateKeyEntry))
      throw new KeyStoreException("Thats not a private key!"); 
    KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry)entry;
    PublicKey publicKey = privateKeyEntry.getCertificate().getPublicKey();
    PrivateKey key = privateKeyEntry.getPrivateKey();
    KeyStore jksStore = KeyStore.getInstance("JKS");
    FileInputStream fileInputStream1 = new FileInputStream("file:/J:/keystore.jks");
    jksStore.load(fileInputStream1, "file:/J:/keystore.jks".toCharArray());
    if (!jksStore.containsAlias("jinice"))
      jksStore.setKeyEntry("jinice", key, "changeit".toCharArray(), privateKeyEntry.getCertificateChain()); 
    return new KeyPair(publicKey, key);
  }
  
  public static KeyPair loadFromJKS() throws KeyStoreException, NoSuchAlgorithmException, UnrecoverableEntryException, IOException, CertificateException {
    KeyStore.ProtectionParameter param = new KeyStore.PasswordProtection("changeit".toCharArray());
    KeyStore.PrivateKeyEntry entry = (KeyStore.PrivateKeyEntry)jkStore.getEntry("jinice", param);
    if (entry == null)
      throw new KeyStoreException("Thats not a private key!"); 
    Certificate cert = entry.getCertificate();
    PublicKey publicKey = cert.getPublicKey();
    return new KeyPair(publicKey, entry.getPrivateKey());
  }
}