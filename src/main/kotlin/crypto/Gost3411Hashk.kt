package crypto

import org.bouncycastle.crypto.digests.GOST3411Digest
import org.bouncycastle.crypto.engines.GOST28147Engine
import org.bouncycastle.crypto.macs.HMac
import org.bouncycastle.crypto.params.KeyParameter
import org.bouncycastle.jce.provider.BouncyCastleProvider
import sun.misc.BASE64Encoder
import java.math.BigInteger
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.security.Security
import java.util.ArrayList
import java.util.Base64

class Gost3411Hashk {
    internal var algo: String? = null
    fun swapString(`in`: String): String {
        var res = ""
        var i = 0
        while (i <= `in`.length - 1) {
            res += `in`[i + 1]
            res += `in`[i]
            i = i + 2
        }
        return res
    }

    @Throws(NoSuchAlgorithmException::class)
    fun hash(data: String): ByteArray {
        Security.addProvider(BouncyCastleProvider())
        val md = MessageDigest.getInstance("GOST3411")
        md.update(data.toByteArray(StandardCharsets.UTF_8))
        return md.digest()
    }

    @Throws(NoSuchAlgorithmException::class)
    fun h(data: String): String {
        Security.addProvider(BouncyCastleProvider())
        val md = MessageDigest.getInstance("GOST3411")
        md.update(data.toByteArray(StandardCharsets.UTF_8))
        val digest = md.digest()
        val out = BigInteger(1, digest)
        //  byte[] outshifted = this.shifting(digest);
        val hex = String.format("%02x", BigInteger(1, digest))
        //    System.out.println(hex);
        return out.toString(16)
    }

    @Throws(NoSuchAlgorithmException::class)
    fun hash_byte(data: String): ByteArray {
        Security.addProvider(BouncyCastleProvider())
        val md = MessageDigest.getInstance("GOST3411")
        md.update(data.toByteArray(StandardCharsets.UTF_8))
        val digest = md.digest()
        val out = BigInteger(1, digest)
        val hex = String.format("%02x", BigInteger(1, digest))
        return digest
    }

    fun base64(input: BigInteger): String {
        return BASE64Encoder().encode(input.toByteArray())
    }

    fun base64(input: ByteArray): String {
        val out = BigInteger(1, input)
        return sun.misc.BASE64Encoder().encode(out.toByteArray())
    }

    @Throws(NoSuchAlgorithmException::class)
    fun h_Base64rfc2045(data: String): String {
        return base64(hash_byte(data))
    }

    @Throws(NoSuchAlgorithmException::class)
    fun h_swapped(data: String): String {
        Security.addProvider(BouncyCastleProvider())
        val md = MessageDigest.getInstance("GOST3411")
        md.update(data.toByteArray(StandardCharsets.UTF_8))
        val digest = md.digest()
        val out = BigInteger(1, digest)
        //  byte[] outshifted = this.shifting(digest);
        val hex = String.format("%02x", BigInteger(1, digest))
        println(hex)
        return swapString(out.toString(16))
    }

    fun hush(input: ByteArray): String {
        val md = GOST3411Digest(GOST28147Engine.getSBox("D-Test"))
        val gMac = HMac(md)
        val key = "GOST3411".toByteArray()
        gMac.init(KeyParameter(key))
        gMac.update(input, 0, input.size)
        val mac = ByteArray(gMac.macSize)
        gMac.doFinal(mac, 0)
        val `in` = BigInteger(1, mac)
        println(`in`.toString(16))
        return `in`.toString(16)
    }

    fun hushBase64rfc2045(input: String): String {
        return base64(hash_byte(input))
    }

    fun hush2(input: ByteArray): String {
        val gMac = HMac(GOST3411Digest(GOST28147Engine.getSBox("D-Test")))
        val key = "keyphrase".toByteArray()
        gMac.init(KeyParameter(key))
        gMac.update(input, 0, input.size)
        val mac = ByteArray(gMac.macSize)
        gMac.doFinal(mac, 0)
        val `in` = BigInteger(1, mac)
        println(`in`.toString(16))
        return `in`.toString(16)
    }


    fun swap(`in`: ByteArray) {
        var i = 0
        while (i <= `in`.size - 1) {
            val accum = `in`[i]
            `in`[i] = `in`[i + 1]
            `in`[i + 1] = accum
            i = i + 2
        }
    }

    companion object {
        fun toByteArray(hexString: String): ByteArray {
            val arrLength = hexString.length shr 1
            val buf = ByteArray(arrLength)
            for (ii in 0 until arrLength) {
                val index = ii shl 1
                val l_digit = hexString.substring(index, index + 2)
                buf[ii] = Integer.parseInt(l_digit, 16).toByte()
            }
            return buf
        }
        private val LOWER_CASE = charArrayOf(0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), '-', 0.toChar(), 0.toChar(), '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar(), 0.toChar())
    }
}


