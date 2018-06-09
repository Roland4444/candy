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

    //    System.out.println(hash.h("message digest"));
       // System.out.println(hash.h("\t<ns:MessageID>db0486d0-3c08-11e5-95e2-d4c9eff07b77</ns:MessageID><ns2:MessagePrimaryContent><ns1:BreachRequest xmlns:ns1=\"urn://x-artefacts-gibdd-gov-ru/breach/root/1.0\"  xmlns:ns2=\"urn://x-artefacts-gibdd-gov-ru/breach/commons/1.0\"  xmlns:ns3=\"urn://x-artefacts-smev-gov-ru/supplementary/commons/1.0.1\" Id=\"PERSONAL_SIGNATURE\"> <ns1:RequestedInformation> <ns2:RegPointNum>Т785ЕС57</ns2:RegPointNum> </ns1:RequestedInformation> <ns1:Governance> <ns2:Name>ГИБДД РФ</ns2:Name> <ns2:Code>GIBDD</ns2:Code> <ns2:OfficialPerson> <ns3:FamilyName>Загурский</ns3:FamilyName> <ns3:FirstName>Андрей</ns3:FirstName> <ns3:Patronymic>Петрович</ns3:Patronymic> </ns2:OfficialPerson></ns1:Governance> </ns1:BreachRequest> </ns2:MessagePrimaryContent>\t<ns:TestMessage/>"));

        String data ="<ns1:SenderProvidedRequestData xmlns:ns1=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" Id=\"SIGNED_BY_CONSUMER\"><ns1:MessageID>db0486d0-3c08-11e5-95e2-d4c9eff07b77</ns1:MessageID><ns2:MessagePrimaryContent xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\"><ns3:BreachRequest xmlns:ns3=\"urn://x-artefacts-gibdd-gov-ru/breach/root/1.0\" Id=\"PERSONAL_SIGNATURE\"><ns3:RequestedInformation><ns4:RegPointNum xmlns:ns4=\"urn://x-artefacts-gibdd-gov-ru/breach/commons/1.0\">Т785ЕС57</ns4:RegPointNum></ns3:RequestedInformation><ns3:Governance><ns5:Name xmlns:ns5=\"urn://x-artefacts-gibdd-gov-ru/breach/commons/1.0\">ГИБДД РФ</ns5:Name><ns6:Code xmlns:ns6=\"urn://x-artefacts-gibdd-gov-ru/breach/commons/1.0\">GIBDD</ns6:Code><ns7:OfficialPerson xmlns:ns7=\"urn://x-artefacts-gibdd-gov-ru/breach/commons/1.0\"><ns8:FamilyName xmlns:ns8=\"urn://x-artefacts-smev-gov-ru/supplementary/commons/1.0.1\">Загурский</ns8:FamilyName><ns9:FirstName xmlns:ns9=\"urn://x-artefacts-smev-gov-ru/supplementary/commons/1.0.1\">Андрей</ns9:FirstName><ns10:Patronymic xmlns:ns10=\"urn://x-artefacts-smev-gov-ru/supplementary/commons/1.0.1\">Петрович</ns10:Patronymic></ns7:OfficialPerson></ns3:Governance></ns3:BreachRequest></ns2:MessagePrimaryContent><ns1:TestMessage></ns1:TestMessage></ns1:SenderProvidedRequestData>";
        assertEquals("fe35e5ef45f09edb49079b12a24c21f126951f0a368238082d2bb4a8168b500a", hash.h(data));
        System.out.println(hash.h(data));
        String data2 ="<ns1:SenderProvidedRequestData xmlns:ns1=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" Id=\"SIGNED_BY_CONSUMER\"><ns1:MessageID>db0486d0-3c08-11e5-95e2-d4c9eff07b77</ns1:MessageID><ns2:MessagePrimaryContent xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\"><ns3:BreachRequest xmlns:ns3=\"urn://x-artefacts-gibdd-gov-ru/breach/root/1.0\" Id=\"PERSONAL_SIGNATURE\"><ns3:RequestedInformation><ns4:RegPointNum xmlns:ns4=\"urn://x-artefacts-gibdd-gov-ru/breach/commons/1.0\">Т785ЕС57</ns4:RegPointNum></ns3:RequestedInformation><ns3:Governance><ns4:Name>ГИБДД РФ</ns4:Name><ns4:Code>GIBDD</ns4:Code><ns4:OfficialPerson><ns5:FamilyName xmlns:ns5=\"urn://x-artefacts-smev-gov-ru/supplementary/commons/1.0.1\">Загурский</ns5:FamilyName><ns5:FirstName>Андрей</ns5:FirstName><ns5:Patronymic>Петрович</ns5:Patronymic></ns4:OfficialPerson></ns3:Governance></ns3:BreachRequest></ns2:MessagePrimaryContent><ns1:TestMessage></ns1:TestMessage></ns1:SenderProvidedRequestData>";
        assertEquals("7bbea855e6066a914313e3d5ea096c8f45c38cb1f274b31dd1c4a414f63c7d69", hash.h(data2));
        System.out.println(hash.h(data2));

        //https://www.freeformatter.com/message-digest.html#ad-output
        //https://cryptii.com/base64-to-hex
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

    @Test
    public void h2() {
    }

    @Test
    public void h_Base64() throws NoSuchAlgorithmException {
        Security.addProvider(new BouncyCastleProvider());
        Gost3411Hash hash = new Gost3411Hash();
        String data ="<ns1:SenderProvidedRequestData xmlns:ns1=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" Id=\"SIGNED_BY_CONSUMER\"><ns1:MessageID>db0486d0-3c08-11e5-95e2-d4c9eff07b77</ns1:MessageID><ns2:MessagePrimaryContent xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\"><ns3:BreachRequest xmlns:ns3=\"urn://x-artefacts-gibdd-gov-ru/breach/root/1.0\" Id=\"PERSONAL_SIGNATURE\"><ns3:RequestedInformation><ns4:RegPointNum xmlns:ns4=\"urn://x-artefacts-gibdd-gov-ru/breach/commons/1.0\">Т785ЕС57</ns4:RegPointNum></ns3:RequestedInformation><ns3:Governance><ns5:Name xmlns:ns5=\"urn://x-artefacts-gibdd-gov-ru/breach/commons/1.0\">ГИБДД РФ</ns5:Name><ns6:Code xmlns:ns6=\"urn://x-artefacts-gibdd-gov-ru/breach/commons/1.0\">GIBDD</ns6:Code><ns7:OfficialPerson xmlns:ns7=\"urn://x-artefacts-gibdd-gov-ru/breach/commons/1.0\"><ns8:FamilyName xmlns:ns8=\"urn://x-artefacts-smev-gov-ru/supplementary/commons/1.0.1\">Загурский</ns8:FamilyName><ns9:FirstName xmlns:ns9=\"urn://x-artefacts-smev-gov-ru/supplementary/commons/1.0.1\">Андрей</ns9:FirstName><ns10:Patronymic xmlns:ns10=\"urn://x-artefacts-smev-gov-ru/supplementary/commons/1.0.1\">Петрович</ns10:Patronymic></ns7:OfficialPerson></ns3:Governance></ns3:BreachRequest></ns2:MessagePrimaryContent><ns1:TestMessage></ns1:TestMessage></ns1:SenderProvidedRequestData>";
        assertEquals("e76oVeYGapFDE+PV6glsj0XDjLHydLMd0cSkFPY8fWk=", "e76oVeYGapFDE+PV6glsj0XDjLHydLMd0cSkFPY8fWk=");
        System.out.print(hash.h_Base64rfc2045(data));
    }

    @Test
    public void base64() throws NoSuchAlgorithmException {
        Gost3411Hash hash = new Gost3411Hash();
        String input = "<ns1:SenderProvidedRequestData xmlns:ns1=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" Id=\"SIGNED_BY_CONSUMER\"><ns1:MessageID>db0486d0-3c08-11e5-95e2-d4c9eff07b77</ns1:MessageID><ns2:MessagePrimaryContent xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\"><ns3:BreachRequest xmlns:ns3=\"urn://x-artefacts-gibdd-gov-ru/breach/root/1.0\" Id=\"PERSONAL_SIGNATURE\"><ns3:RequestedInformation><ns4:RegPointNum xmlns:ns4=\"urn://x-artefacts-gibdd-gov-ru/breach/commons/1.0\">Т785ЕС57</ns4:RegPointNum></ns3:RequestedInformation><ns3:Governance><ns4:Name>ГИБДД РФ</ns4:Name><ns4:Code>GIBDD</ns4:Code><ns4:OfficialPerson><ns5:FamilyName xmlns:ns5=\"urn://x-artefacts-smev-gov-ru/supplementary/commons/1.0.1\">Загурский</ns5:FamilyName><ns5:FirstName>Андрей</ns5:FirstName><ns5:Patronymic>Петрович</ns5:Patronymic></ns4:OfficialPerson></ns3:Governance></ns3:BreachRequest></ns2:MessagePrimaryContent><ns1:TestMessage></ns1:TestMessage></ns1:SenderProvidedRequestData>";
        assertEquals("e76oVeYGapFDE+PV6glsj0XDjLHydLMd0cSkFPY8fWk=", hash.base64(hash.hash_byte(input)));

    }

    @Test
    public void hash_byte() throws NoSuchAlgorithmException {
        Gost3411Hash hash = new Gost3411Hash();

        String input = "<ns1:SenderProvidedRequestData xmlns:ns1=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" Id=\"SIGNED_BY_CONSUMER\"><ns1:MessageID>db0486d0-3c08-11e5-95e2-d4c9eff07b77</ns1:MessageID><ns2:MessagePrimaryContent xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\"><ns3:BreachRequest xmlns:ns3=\"urn://x-artefacts-gibdd-gov-ru/breach/root/1.0\" Id=\"PERSONAL_SIGNATURE\"><ns3:RequestedInformation><ns4:RegPointNum xmlns:ns4=\"urn://x-artefacts-gibdd-gov-ru/breach/commons/1.0\">Т785ЕС57</ns4:RegPointNum></ns3:RequestedInformation><ns3:Governance><ns5:Name xmlns:ns5=\"urn://x-artefacts-gibdd-gov-ru/breach/commons/1.0\">ГИБДД РФ</ns5:Name><ns6:Code xmlns:ns6=\"urn://x-artefacts-gibdd-gov-ru/breach/commons/1.0\">GIBDD</ns6:Code><ns7:OfficialPerson xmlns:ns7=\"urn://x-artefacts-gibdd-gov-ru/breach/commons/1.0\"><ns8:FamilyName xmlns:ns8=\"urn://x-artefacts-smev-gov-ru/supplementary/commons/1.0.1\">Загурский</ns8:FamilyName><ns9:FirstName xmlns:ns9=\"urn://x-artefacts-smev-gov-ru/supplementary/commons/1.0.1\">Андрей</ns9:FirstName><ns10:Patronymic xmlns:ns10=\"urn://x-artefacts-smev-gov-ru/supplementary/commons/1.0.1\">Петрович</ns10:Patronymic></ns7:OfficialPerson></ns3:Governance></ns3:BreachRequest></ns2:MessagePrimaryContent><ns1:TestMessage></ns1:TestMessage></ns1:SenderProvidedRequestData>";
        assertNotEquals(null, hash.hash_byte(input));
    }

    @Test
    public void base64rfc2045() throws NoSuchAlgorithmException {
        Gost3411Hash hash = new Gost3411Hash();
        String input = "<ns1:SenderProvidedRequestData xmlns:ns1=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" Id=\"SIGNED_BY_CONSUMER\"><ns1:MessageID>db0486d0-3c08-11e5-95e2-d4c9eff07b77</ns1:MessageID><ns2:MessagePrimaryContent xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\"><ns3:BreachRequest xmlns:ns3=\"urn://x-artefacts-gibdd-gov-ru/breach/root/1.0\" Id=\"PERSONAL_SIGNATURE\"><ns3:RequestedInformation><ns4:RegPointNum xmlns:ns4=\"urn://x-artefacts-gibdd-gov-ru/breach/commons/1.0\">Т785ЕС57</ns4:RegPointNum></ns3:RequestedInformation><ns3:Governance><ns4:Name>ГИБДД РФ</ns4:Name><ns4:Code>GIBDD</ns4:Code><ns4:OfficialPerson><ns5:FamilyName xmlns:ns5=\"urn://x-artefacts-smev-gov-ru/supplementary/commons/1.0.1\">Загурский</ns5:FamilyName><ns5:FirstName>Андрей</ns5:FirstName><ns5:Patronymic>Петрович</ns5:Patronymic></ns4:OfficialPerson></ns3:Governance></ns3:BreachRequest></ns2:MessagePrimaryContent><ns1:TestMessage></ns1:TestMessage></ns1:SenderProvidedRequestData>";
        assertEquals("e76oVeYGapFDE+PV6glsj0XDjLHydLMd0cSkFPY8fWk=", hash.base64(hash.hash_byte(input)));
    }

}