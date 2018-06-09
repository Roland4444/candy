package util

import org.junit.Test

import org.junit.Assert.*

class SAAJkTest {
    @Test
    @Throws(Exception::class)
    fun send() {
        val saa = SAAJk("http://smev3-n0.test.gosuslugi.ru:7500/ws?wsdl")
        assertNotEquals(null, saa.send("12.xml", "results.xml"))
    }
}