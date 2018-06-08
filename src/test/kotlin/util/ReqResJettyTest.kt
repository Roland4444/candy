package util

import org.junit.Test

class ReqResJettyTest {

    @Test
    fun clientInit() {
        val exc = ReqResJetty("http://smev3-n0.test.gosuslugi.ru:7500/smev/v1.1/ws")
        exc.exchange("init.xml","answer.xml")

    }
}