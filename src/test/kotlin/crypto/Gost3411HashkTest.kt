package crypto

import org.junit.Test

import org.junit.Assert.*

class Gost3411HashkTest {

    @Test
    fun hushBase64rfc2045() {
        val hasher = Gost3411Hashk()
        val input = "<ns1:SenderProvidedRequestData xmlns:ns1=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\" Id=\"SIGNED_BY_CONSUMER\"><ns1:MessageID>db0486d0-3c08-11e5-95e2-d4c9eff07b77</ns1:MessageID><ns2:MessagePrimaryContent xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\"><ns3:BreachRequest xmlns:ns3=\"urn://x-artefacts-gibdd-gov-ru/breach/root/1.0\" Id=\"PERSONAL_SIGNATURE\"><ns3:RequestedInformation><ns4:RegPointNum xmlns:ns4=\"urn://x-artefacts-gibdd-gov-ru/breach/commons/1.0\">Т785ЕС57</ns4:RegPointNum></ns3:RequestedInformation><ns3:Governance><ns4:Name>ГИБДД РФ</ns4:Name><ns4:Code>GIBDD</ns4:Code><ns4:OfficialPerson><ns5:FamilyName xmlns:ns5=\"urn://x-artefacts-smev-gov-ru/supplementary/commons/1.0.1\">Загурский</ns5:FamilyName><ns5:FirstName>Андрей</ns5:FirstName><ns5:Patronymic>Петрович</ns5:Patronymic></ns4:OfficialPerson></ns3:Governance></ns3:BreachRequest></ns2:MessagePrimaryContent><ns1:TestMessage></ns1:TestMessage></ns1:SenderProvidedRequestData>"
        assertEquals("e76oVeYGapFDE+PV6glsj0XDjLHydLMd0cSkFPY8fWk=", hasher.h_Base64rfc2045(input))
    }
}