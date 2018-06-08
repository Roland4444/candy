package util
import org.apache.http.HttpEntity
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity
import org.apache.http.util.EntityUtils
import org.apache.http.impl.client.DefaultHttpClient
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream


class ReqResApache {


    fun exchange():String{
        val client = DefaultHttpClient()
        val post = HttpPost("http://smev3-n0.test.gosuslugi.ru:7500/smev/v1.1/ws")
        val xml = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\">\n" +
                "   <S:Body>\n" +
                "      <ns2:SendRequestRequest xmlns:ns3=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/faults/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\">\n" +
                "         <ns:SenderProvidedRequestData Id=\"SIGNED_BY_CONSUMER\" xmlns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\">\t<ns:MessageID>db0486d0-3c08-11e5-95e2-d4c9eff07b77</ns:MessageID><ns2:MessagePrimaryContent><ns1:BreachRequest xmlns:ns1=\"urn://x-artefacts-gibdd-gov-ru/breach/root/1.0\"  xmlns:ns2=\"urn://x-artefacts-gibdd-gov-ru/breach/commons/1.0\"  xmlns:ns3=\"urn://x-artefacts-smev-gov-ru/supplementary/commons/1.0.1\" Id=\"PERSONAL_SIGNATURE\"> <ns1:RequestedInformation> <ns2:RegPointNum>Т785ЕС57</ns2:RegPointNum> </ns1:RequestedInformation> <ns1:Governance> <ns2:Name>ГИБДД РФ</ns2:Name> <ns2:Code>GIBDD</ns2:Code> <ns2:OfficialPerson> <ns3:FamilyName>Загурский</ns3:FamilyName> <ns3:FirstName>Андрей</ns3:FirstName> <ns3:Patronymic>Петрович</ns3:Patronymic> </ns2:OfficialPerson></ns1:Governance> </ns1:BreachRequest> </ns2:MessagePrimaryContent>\t<ns:TestMessage/></ns:SenderProvidedRequestData>\n" +
                "         <ns2:CallerInformationSystemSignature><ds:Signature xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411\"/><ds:Reference URI=\"#SIGNED_BY_CONSUMER\"><ds:Transforms><ds:Transform Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:Transform Algorithm=\"urn://smev-gov-ru/xmldsig/transform\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#gostr3411\"/><ds:DigestValue>/jXl70XwnttJB5sSokwh8SaVHwo2gjgILSu0qBaLUAo=</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>J3746ks34pOcPGQpKzc0sz3n9+gjPtzZbSEEs4c3sTwbtfdaY7N/hxXzEIvXc+3ad9bc35Y8yBhZ/BYbloGt+Q==</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>MIIBcDCCAR2gAwIBAgIEHVmVKDAKBgYqhQMCAgMFADAtMRAwDgYDVQQLEwdTWVNURU0xMQwwCgYDVQQKEwNJUzIxCzAJBgNVBAYTAlJVMB4XDTE1MDUwNzEyMTUzMFoXDTE4MDUwNjEyMTUzMFowLTEQMA4GA1UECxMHU1lTVEVNMTEMMAoGA1UEChMDSVMyMQswCQYDVQQGEwJSVTBjMBwGBiqFAwICEzASBgcqhQMCAiMBBgcqhQMCAh4BA0MABEDoWGZlTUWD43G1N7TEm14+QyXrJWProrzoDoCJRem169q4bezFOUODcNooQJNg3PtAizkWeFcX4b93u8fpVy7RoyEwHzAdBgNVHQ4EFgQUaRG++MAcPZvK/E2vR1BBl5G7s5EwCgYGKoUDAgIDBQADQQCg25vA3RJL3kgcJhVOHA86vnkMAtZYr6HBPa7LpEo0HJrbBF0ygKk50app1lzPdZ5TtK2itfmNgTYiuQHX3+nE</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></ns2:CallerInformationSystemSignature>\n" +
                "      </ns2:SendRequestRequest>\n" +
                "   </S:Body>\n" +
                "</S:Envelope>"

        val converted= Convert__()
        val entity2 = converted.convertToByteArray(xml)//ByteArrayEntity(xml.replace("^\uFEFF", "").toByteArray(StandardCharsets.UTF_8))
        val entity = ByteArrayEntity(entity2)
        post.setEntity(entity)
        val response = client.execute(post)
        val result = EntityUtils.toString(response.entity)
        print(result)
        return result
    }
}

/*@@    https://stackoverflow.com/questions/18188041/write-in-body-request-with-httpclient
 public void post() throws Exception{
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost("http://www.baidu.com");
        String xml = "<xml>xxxx</xml>";
        HttpEntity entity = new ByteArrayEntity(xml.getBytes("UTF-8"));
        post.setEntity(entity);
        HttpResponse response = client.execute(post);
        String result = EntityUtils.toString(response.getEntity());
    }





*/