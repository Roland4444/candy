import javax.xml.soap.*

object SOAPClientSAAJ {

    // SAAJ - SOAP Client Testing
    @JvmStatic
    fun main(args: Array<String>) {
        /*
            The example below requests from the Web Service at:
             http://www.webservicex.net/uszip.asmx?op=GetInfoByCity
http://smev3-n0.test.gosuslugi.ru:7500/smev/v1.1/ws

            To call other WS, change the parameters below, which are:
             - the SOAP Endpoint URL (that is, where the service is responding from)
             - the SOAP Action

            Also change the contents of the method createSoapEnvelope() in this class. It constructs
             the inner part of the SOAP envelope that is actually sent.
         */
        val soapEndpointUrl = "http://smev3-n0.test.gosuslugi.ru:7500/smev/v1.1/ws"
        val soapAction = "http://smev3-n0.test.gosuslugi.ru:7500/smev/v1.1/SendRequest"

        callSoapWebService(soapEndpointUrl, soapAction)
    }

    @Throws(SOAPException::class)
    private fun createSoapEnvelope(soapMessage: SOAPMessage) {
        val soapPart = soapMessage.soapPart

        val myNamespace = "myNamespace"
        val myNamespaceURI = "urn://x-artefacts-smev-gov-ru/services/message-exchange/1.1"

        // SOAP Envelope
        val envelope = soapPart.envelope
        envelope.body.textContent = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns1=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\">\n" +
                "   <soapenv:Header/>\n" +
                "   <soapenv:Body>\n" +
                "      <ns:GetRequestRequest>\n" +
                "         <ns1:MessageTypeSelector Id=\"?\">\n" +
                "            <ns1:NamespaceURI>?</ns1:NamespaceURI>\n" +
                "            <ns1:RootElementLocalName>?</ns1:RootElementLocalName>\n" +
                "            <ns1:Timestamp>?</ns1:Timestamp>\n" +
                "            <!--Optional:-->\n" +
                "            <ns1:NodeID>?</ns1:NodeID>\n" +
                "         </ns1:MessageTypeSelector>\n" +
                "         <!--Optional:-->\n" +
                "         <ns:CallerInformationSystemSignature>\n" +
                "            <!--You may enter ANY elements at this point-->\n" +
                "         </ns:CallerInformationSystemSignature>\n" +
                "      </ns:GetRequestRequest>\n" +
                "   </soapenv:Body>\n" +
                "</soapenv:Envelope>"
        /*
            Constructed SOAP Request Message:
            <SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" xmlns:myNamespace="http://www.webserviceX.NET">
                <SOAP-ENV:Header/>
                <SOAP-ENV:Body>
                    <myNamespace:GetInfoByCity>
                        <myNamespace:USCity>New York</myNamespace:USCity>
                    </myNamespace:GetInfoByCity>
                </SOAP-ENV:Body>
            </SOAP-ENV:Envelope>
            */

        // SOAP Body
        val soapBody = envelope.body
    }

    private fun callSoapWebService(soapEndpointUrl: String, soapAction: String) {
        try {
            // Create SOAP Connection
            val soapConnectionFactory = SOAPConnectionFactory.newInstance()
            val soapConnection = soapConnectionFactory.createConnection()

            // Send SOAP Message to SOAP Server
            val soapResponse = soapConnection.call(createSOAPRequest(soapAction), soapEndpointUrl)

            // Print the SOAP Response
            println("Response SOAP Message:")
            soapResponse.writeTo(System.out)
            println()

            soapConnection.close()
        } catch (e: Exception) {
            System.err.println("\nError occurred while sending SOAP Request to Server!\nMake sure you have the correct endpoint URL and SOAPAction!\n")
            e.printStackTrace()
        }

    }

    @Throws(Exception::class)
    private fun createSOAPRequest(soapAction: String): SOAPMessage {
        val messageFactory = MessageFactory.newInstance()
        val soapMessage = messageFactory.createMessage()

        createSoapEnvelope(soapMessage)

        val headers = soapMessage.mimeHeaders
        headers.addHeader("SOAPAction", soapAction)

        soapMessage.saveChanges()

        /* Print the request message, just for debugging purposes */
        println("Request SOAP Message:")
        soapMessage.writeTo(System.out)
        println("\n")

        return soapMessage
    }

}