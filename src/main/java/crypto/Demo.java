package crypto;

import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.OperatorCreationException;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.Security;
import java.security.cert.X509Certificate;

import static java.time.LocalDate.now;

public class Demo {
  public static void main(String[] args) throws GeneralSecurityException, IOException, OperatorCreationException, CMSException {
    Security.addProvider(new BouncyCastleProvider());

    Crypto crypto = new GOSTCrypto();
    KeyPair root = crypto.generateKeyPair();
    X509Certificate rootCert = crypto.issueSelfSignedCert(root, "Root", now().plusYears(5));

    KeyPair subject = crypto.generateKeyPair();
    X509Certificate subjectCert = crypto.issueCert(root, rootCert, subject.getPublic(), "Roman Pastushkov", BigInteger.ONE, now().plusYears(1));
    System.out.println("cert>>>>>>>>>>>>>"+subjectCert);
    System.out.println("************************************************END CERTIFICATE*******************");



    crypto.toPEM(subjectCert);
    FileWriter wr = new FileWriter("cert.pem");
    wr.write(crypto.toPEM(subjectCert));
    wr.close();


    byte[] signature = crypto.sign("hello", subject.getPrivate());
    System.out.println(signature.length);

    CMSSignedData cades = crypto.signCades("hello", subject.getPrivate(), subjectCert);
    System.out.println(cades.getEncoded().length);
  }
}
