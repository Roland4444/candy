package util

import javax.xml.soap.*
import javax.xml.transform.TransformerFactory
import javax.xml.transform.stream.StreamResult
import javax.xml.transform.stream.StreamSource
import java.io.FileInputStream
import java.io.FileOutputStream


class SAAJk(private val url: String) {
    @Throws(Exception::class)
    private fun createSoapResponse(soapResponse: SOAPMessage, results: String) {
        val transformerFactory = TransformerFactory.newInstance()
        val transformer = transformerFactory.newTransformer()
        val sourceContent = soapResponse.soapPart.content
        println("\n----------SOAP Response-----------")
        val result = StreamResult(FileOutputStream(results))
        transformer.transform(sourceContent, result)
        val console = StreamResult(System.out)
        transformer.transform(sourceContent, console)
    }

    @Throws(Exception::class)
    fun send(filename: String, result: String): String {
        val soapConnFactory = SOAPConnectionFactory.newInstance()
        val connection = soapConnFactory.createConnection()
        val messageFactory = MessageFactory.newInstance()
        val message = messageFactory.createMessage()
        val soapPart = message.soapPart
        val preppedMsgSrc = StreamSource(FileInputStream(filename))
        soapPart.content = preppedMsgSrc
        message.saveChanges()
        println("\nREQUEST:\n")
        message.writeTo(System.out)
        println()
        val reply = connection.call(message, this.url)
        createSoapResponse(reply, result)
        connection.close()
        return reply.soapBody.toString()
    }
}