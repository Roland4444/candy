package util

import org.junit.Test

import org.junit.Assert.*
import java.io.FileInputStream
import java.io.FileOutputStream

class ReqResApacheTest {

    @Test
    fun excahnge() {

        val transForm = SmevImportedTransForm()
        val inppt = FileInputStream("1.xml")
        val out = FileOutputStream("1_trans,xml")
        transForm.process(inppt, out)
        val ap=ReqResApache()
        assertNotEquals(null, ap.exchange())
    }
}