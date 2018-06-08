package crypto;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.Test;
import org.omg.CORBA.portable.InputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.Base64.Encoder;

import static org.junit.Assert.*;

public class Gost3411HashTest  {

    @Test
    public void h() throws NoSuchAlgorithmException {

        Gost3411Hash hash = new Gost3411Hash();
        assertNotEquals(null, hash.h(""));
   //     System.out.println(hash.h(""));
        assertNotEquals(null, hash.h("abc"));
   //     System.out.println(hash.h("abc"));
        assertNotEquals(null, hash.h("message digest"));
    //    System.out.println(hash.h("message digest"));
        System.out.println(hash.h("\t<ns:MessageID>db0486d0-3c08-11e5-95e2-d4c9eff07b77</ns:MessageID><ns2:MessagePrimaryContent><ns1:BreachRequest xmlns:ns1=\"urn://x-artefacts-gibdd-gov-ru/breach/root/1.0\"  xmlns:ns2=\"urn://x-artefacts-gibdd-gov-ru/breach/commons/1.0\"  xmlns:ns3=\"urn://x-artefacts-smev-gov-ru/supplementary/commons/1.0.1\" Id=\"PERSONAL_SIGNATURE\"> <ns1:RequestedInformation> <ns2:RegPointNum>Т785ЕС57</ns2:RegPointNum> </ns1:RequestedInformation> <ns1:Governance> <ns2:Name>ГИБДД РФ</ns2:Name> <ns2:Code>GIBDD</ns2:Code> <ns2:OfficialPerson> <ns3:FamilyName>Загурский</ns3:FamilyName> <ns3:FirstName>Андрей</ns3:FirstName> <ns3:Patronymic>Петрович</ns3:Patronymic> </ns2:OfficialPerson></ns1:Governance> </ns1:BreachRequest> </ns2:MessagePrimaryContent>\t<ns:TestMessage/>"));
    }

    @Test
    public void base64test() throws IOException {
        Gost3411Hash mx = new Gost3411Hash();
        FileInputStream fileInputStream = null;
        byte[]  bytesArray = new byte[(int)  new File("TRANS.xml").length()];
        fileInputStream = new FileInputStream(new File("TRANS.xml"));
        fileInputStream.read(bytesArray);
        assertNotEquals(0, bytesArray.length);
        assertNotEquals(null,  mx.hushBase64(bytesArray));
        System.out.print(mx.hushBase64(bytesArray));
    }




    @Test
    public void shift() {
        byte a;
        a=10;
        int i;
        i = a << 4;
        System.out.println("Original value of a: " + a);
        assertEquals(i,160);
    }

    @Test
    public void shift1() {
        Gost3411Hash m = new Gost3411Hash();
        byte[] a={10};
        assertEquals(160, m.shifting(a).get(0));
        byte[] b={16};
        assertEquals(1, m.shifting(b).get(0));
    }

    @Test
    public void swap() {
        int a;
        a = 0x10;
        int shift = 8; // сдвиг в битах
        a = (a>>(32 - shift)) | a << 8;// цикло сдвиг влево на 8 бит
        System.out.println(Integer.toHexString(a));
    }

    @Test
    public void swapString() {
        Gost3411Hash m = new Gost3411Hash();
        assertEquals("89e1", m.swapString("981e"));
    }

    @Test
    public void h1() throws NoSuchAlgorithmException {
        Security.addProvider(new BouncyCastleProvider());
        MessageDigest md = MessageDigest.getInstance( "GOST3411" );
        String text = "";
        md.update( text.getBytes( StandardCharsets.UTF_8 ) );
        byte[] digest = md.digest();
        BigInteger out = new BigInteger( 1, digest );

        String hex = String.format( "%064x", new BigInteger( 1, digest ) );
        //   System.out.println(out.toString(16));
        Gost3411Hash m= new Gost3411Hash();
        byte[] gen = m.toByteArray(m.swapString(out.toString(16)));
        BigInteger out2 = new BigInteger( 1, gen );
        BigInteger out3 = new BigInteger(  m.toByteArray(m.swapString(out.toString(16))));
        assertEquals(out, out3);

    }
}