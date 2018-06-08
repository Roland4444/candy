package crypto;

import org.bouncycastle.crypto.digests.GOST3411Digest;
import org.bouncycastle.crypto.engines.GOST28147Engine;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Base64.Encoder;

public class Gost3411Hash {
    String algo;

    public static final byte[] toByteArray( String hexString )
    {
        int arrLength = hexString.length() >> 1;
        byte buf[] = new byte[arrLength];

        for ( int ii = 0; ii < arrLength; ii++ )
        {
            int index = ii << 1;
            String l_digit = hexString.substring( index, index + 2 );
            buf[ii] = ( byte ) Integer.parseInt( l_digit, 16 );
        }

        return buf;
    }

    public String swapString(String in){
        String res="";
        int i=0;
        while (i<=in.length()-1){
            res+=in.charAt(i+1);
            res+=in.charAt(i);
            i=i+2;
        }
        return res;
    }

    public byte[] hash(String data) throws NoSuchAlgorithmException {
        Security.addProvider(new BouncyCastleProvider());
        MessageDigest md = MessageDigest.getInstance( "GOST3411" );
        md.update( data.getBytes( StandardCharsets.UTF_8 ) );
        byte[] digest = md.digest();
        return digest;
    }




    public String h(String data) throws NoSuchAlgorithmException {
        Security.addProvider(new BouncyCastleProvider());
        MessageDigest md = MessageDigest.getInstance( "GOST3411" );
        md.update( data.getBytes( StandardCharsets.UTF_8 ) );
        byte[] digest = md.digest();
        BigInteger out = new BigInteger( 1, digest );
      //  byte[] outshifted = this.shifting(digest);
        String hex = String.format( "%02x", new BigInteger( 1, digest ) );
        System.out.println(hex);
        return swapString(out.toString(16));
    }


    public String hush(byte[] input){
        GOST3411Digest md = new GOST3411Digest(GOST28147Engine.getSBox("D-Test"));
        HMac gMac= new HMac(md);
        byte[] key = "GOST3411".getBytes();
        gMac.init(new KeyParameter(key));
        gMac.update(input, 0, input.length);
        byte[] mac = new byte[gMac.getMacSize()];
        gMac.doFinal(mac, 0);
        BigInteger in = new BigInteger(1, mac);
        System.out.println(in.toString(16));
        return in.toString(16);
    }

    public String hushBase64(byte[] input){
        GOST3411Digest md = new GOST3411Digest(GOST28147Engine.getSBox("D-Test"));
        HMac gMac= new HMac(md);
        byte[] key = "GOST3411".getBytes();
        gMac.init(new KeyParameter(key));
        gMac.update(input, 0, input.length);
        byte[] mac = new byte[gMac.getMacSize()];
        gMac.doFinal(mac, 0);
        BigInteger in = new BigInteger(1, mac);
        return Base64.getEncoder().encodeToString(mac);
    }

    public String hush2(byte[] input){
        HMac gMac= new HMac(new GOST3411Digest(GOST28147Engine.getSBox("D-Test")));
        byte[] key = "keyphrase".getBytes();
        gMac.init(new KeyParameter(key));
        gMac.update(input, 0, input.length);
        byte[] mac = new byte[gMac.getMacSize()];
        gMac.doFinal(mac, 0);
        BigInteger in = new BigInteger(1, mac);
        System.out.println(in.toString(16));
        return in.toString(16);
    }


    private static final char[] LOWER_CASE =
            {
                    0,   0,   0,   0,   0,   0,   0,   0,
                    0,   0,   0,   0,   0,   0,   0,   0,
                    0,   0,   0,   0,   0,   0,   0,   0,
                    0,   0,   0,   0,   0,   0,   0,   0,
                    0,   0,   0,   0,   0,   0,   0,   0,
                    0,   0,   0,   0,   0, '-',   0,   0,
                    '0', '1', '2', '3', '4', '5', '6', '7',
                    '8', '9',   0,   0,   0,   0,   0,   0,
                    0, 'a', 'b', 'c', 'd', 'e', 'f', 'g',
                    'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
                    'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
                    'x', 'y', 'z',   0,   0,   0,   0,   0,
                    0, 'a', 'b', 'c', 'd', 'e', 'f', 'g',
                    'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
                    'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
                    'x', 'y', 'z',   0,   0,   0,   0,   0,
                    0,   0,   0,   0,   0,   0,   0,   0,
                    0,   0,   0,   0,   0,   0,   0,   0,
                    0,   0,   0,   0,   0,   0,   0,   0,
                    0,   0,   0,   0,   0,   0,   0,   0,
                    0,   0,   0,   0,   0,   0,   0,   0,
                    0,   0,   0,   0,   0,   0,   0,   0,
                    0,   0,   0,   0,   0,   0,   0,   0,
                    0,   0,   0,   0,   0,   0,   0,   0
            };





    public void swap(byte[] in){
        int i=0;
        while (i<=in.length-1){
            byte accum=in[i];
            in[i]=in[i+1];
            in[i+1]=accum;
            i=i+2;
        }
    }
    public ArrayList shifting(byte[] in){
        ArrayList out = new ArrayList();
        for (int i=1; i<=in.length; i++){
            out.add(in[i-1]<<4);
        }
        return out;
    }
}


