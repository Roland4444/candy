package util;

import org.junit.Assert
import org.junit.Test;
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.FileWriter
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths



public class transFromSuckersTest {
    @Test
    fun tarnsformtoSign(){
        val test = transFromSuckers()
        val `in` = FileInputStream("SendRequestRequestNoAttach.xml")
        val out = FileOutputStream("TRANS.xml")
        test.process(`in`, out)

    }

    @Test
    fun tarnsformHighligthedData(){
        val test = transFromSuckers()
        val `in` = FileInputStream("1.xml")
        val out = FileOutputStream("TRANS.xml")
        test.process(`in`, out)
    }

    @Test
    fun processini() {
        val test = transFromSuckers()
        val wr = FileWriter("input.xml")
        val write = "<ns2:SenderProvidedRequestData xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.0\" Id=\"SIGNED_BY_CONSUMER\">\n" +
                " <MessagePrimaryContent xmlns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.0\">\n" +
                "  <SomeRequest:SomeRequest xmlns:SomeRequest=\"urn://x-artifacts-it-ru/vs/smev/test/test-business-data/1.0\">\n" +
                "   <x xmlns=\"urn://x-artifacts-it-ru/vs/smev/test/test-business-data/1.0\">qweqwe</x>\n" +
                "  </SomeRequest:SomeRequest>\n" +
                " </MessagePrimaryContent>\n" +
                "</ns2:SenderProvidedRequestData>"
        wr.write(write)
        wr.close()
        val `in` = FileInputStream("input.xml")
        val out = FileOutputStream("out.xml")
        test.process(`in`, out)
        val contentBuilder = StringBuilder()
        val stream = Files.lines(Paths.get("out.xml"), StandardCharsets.UTF_8)
        run { stream.forEach { s -> contentBuilder.append(s).append("") } }
        val etalon = "<ns1:SenderProvidedRequestData xmlns:ns1=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.0\" Id=\"SIGNED_BY_CONSUMER\"><ns2:MessagePrimaryContent xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.0\"><ns3:SomeRequest xmlns:ns3=\"urn://x-artifacts-it-ru/vs/smev/test/test-business-data/1.0\"><ns3:x>qweqwe</ns3:x></ns3:SomeRequest></ns2:MessagePrimaryContent></ns1:SenderProvidedRequestData>"
        Assert.assertEquals(etalon.length.toLong(), contentBuilder.toString().length.toLong())
        Assert.assertEquals(etalon, contentBuilder.toString())
    }

    @Test
    fun process() {
        val test = transFromSuckers()
        val wr = FileWriter("input.xml")
        val write = "<ns2:SenderProvidedRequestData xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.0\" Id=\"SIGNED_BY_CONSUMER\">\n" +
                " <MessagePrimaryContent xmlns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.0\">\n" +
                "  <SomeRequest:SomeRequest xmlns:SomeRequest=\"urn://x-artifacts-it-ru/vs/smev/test/test-business-data/1.0\">\n" +
                "   <x xmlns=\"urn://x-artifacts-it-ru/vs/smev/test/test-business-data/1.0\">qweqwe</x>\n" +
                "  </SomeRequest:SomeRequest>\n" +
                " </MessagePrimaryContent>\n" +
                "</ns2:SenderProvidedRequestData>"
        wr.write(write)
        wr.close()
        val `in` = FileInputStream("input.xml")
        val out = FileOutputStream("out.xml")
        test.process(`in`, out)
        val contentBuilder = StringBuilder()
        val stream = Files.lines(Paths.get("out.xml"), StandardCharsets.UTF_8)
        run { stream.forEach { s -> contentBuilder.append(s).append("") } }
        val etalon = "<ns1:SenderProvidedRequestData xmlns:ns1=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.0\" Id=\"SIGNED_BY_CONSUMER\"><ns2:MessagePrimaryContent xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.0\"><ns3:SomeRequest xmlns:ns3=\"urn://x-artifacts-it-ru/vs/smev/test/test-business-data/1.0\"><ns3:x>qweqwe</ns3:x></ns3:SomeRequest></ns2:MessagePrimaryContent></ns1:SenderProvidedRequestData>"
        Assert.assertEquals(etalon, contentBuilder.toString())
    }

    @Test
    fun process2() {
        val test = transFromSuckers()
        val wr = FileWriter("input.xml")
        val write = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<!-- Тестирование правил 1, 2, 6: \n" +
                "\t- XML declaration выше, этот комментарий, и следующая за ним processing instruction должны быть вырезаны;\n" +
                "\t- Переводы строки должны быть удалены;\n" +
                "\t- Namespace prefixes заменяются на автоматически сгенерированные.\n" +
                "-->\n" +
                "<?xml-stylesheet type=\"text/xsl\" href=\"style.xsl\"?>\n" +
                "\n" +
                "<elementOne xmlns=\"http://test/1\">\n" +
                "\t<qwe:elementTwo xmlns:qwe=\"http://test/2\">asd</qwe:elementTwo>  \n" +
                "</elementOne>"
        wr.write(write)
        wr.close()
        val `in` = FileInputStream("input.xml")
        val out = FileOutputStream("out.xml")
        test.process(`in`, out)
        val contentBuilder = StringBuilder()
        val stream = Files.lines(Paths.get("out.xml"), StandardCharsets.UTF_8)
        run { stream.forEach { s -> contentBuilder.append(s).append("") } }
        val etalon = "<ns1:elementOne xmlns:ns1=\"http://test/1\"><ns2:elementTwo xmlns:ns2=\"http://test/2\">asd</ns2:elementTwo></ns1:elementOne>"
        Assert.assertEquals(etalon, contentBuilder.toString())
    }


    @Test
    fun processEquals() {
        val test = transFromSuckers()
        val wr = FileWriter("input.xml")
        val example1 = "<?xml version=\\\"1.0\\\" encoding=\\\"UTF-8\\\"?>\\n\" +\n" +
                "                \"<!-- \\n\" +\n" +
                "                \"\\tВсё то же, что в test case 1, плюс правила 3, 7 и 8:\\n\" +\n" +
                "                \"\\t- Атрибуты должны быть отсортированы в алфавитном порядке: сначала по namespace URI (если атрибут - в qualified form), затем – по local name. \\n\" +\n" +
                "                \"\\t\\tАтрибуты в unqualified form после сортировки идут после атрибутов в qualified form.\\n\" +\n" +
                "                \"\\t- Объявления namespace prefix должны находиться перед атрибутами. Объявления префиксов должны быть отсортированы в порядке объявления, а именно:\\n\" +\n" +
                "                \"\\t\\ta.\\tПервым объявляется префикс пространства имен элемента, если он не был объявлен выше по дереву.\\n\" +\n" +
                "                \"\\t\\tb.\\tДальше объявляются префиксы пространств имен атрибутов, если они требуются. \\n\" +\n" +
                "                \"\\t\\t\\tПорядок этих объявлений соответствует порядку атрибутов, отсортированных в алфавитном порядке (см. п.5).\\n\" +\n" +
                "                \"-->\\n\" +\n" +
                "                \"<?xml-stylesheet type=\\\"text/xsl\\\" href=\\\"style.xsl\\\"?>\\n\" +\n" +
                "                \"\\n\" +\n" +
                "                \"<elementOne xmlns=\\\"http://test/1\\\" xmlns:qwe=\\\"http://test/2\\\" xmlns:asd=\\\"http://test/3\\\">\\n\" +\n" +
                "                \"\\t<qwe:elementTwo>\\n\" +\n" +
                "                \"\\t\\t<asd:elementThree xmlns:wer=\\\"http://test/a\\\" xmlns:zxc=\\\"http://test/0\\\" wer:attZ=\\\"zzz\\\" attB=\\\"bbb\\\" attA=\\\"aaa\\\" zxc:attC=\\\"ccc\\\" asd:attD=\\\"ddd\\\" asd:attE=\\\"eee\\\" qwe:attF=\\\"fff\\\"/>\\n\" +\n" +
                "                \"\\t</qwe:elementTwo>  \\n\" +\n" +
                "                \"</elementOne>"
        val example2 = "<?xml version=\\\"1.0\\\" encoding=\\\"UTF-8\\\"?>\\n\" +\n" +
                "                \"<!-- \\n\" +\n" +
                "                \"\\tВсё то же, что в test case 1, плюс правила 3, 7 и 8:\\n\" +\n" +
                "                \"\\t- Атрибуты должны быть отсортированы в алфавитном порядке: сначала по namespace URI (если атрибут - в qualified form), затем – по local name. \\n\" +\n" +
                "                \"\\t\\tАтрибуты в unqualified form после сортировки идут после атрибутов в qualified form.\\n\" +\n" +
                "                \"\\t- Объявления namespace prefix должны находиться перед атрибутами. Объявления префиксов должны быть отсортированы в порядке объявления, а именно:\\n\" +\n" +
                "                \"\\t\\ta.\\tПервым объявляется префикс пространства имен элемента, если он не был объявлен выше по дереву.\\n\" +\n" +
                "                \"\\t\\tb.\\tДальше объявляются префиксы пространств имен атрибутов, если они требуются. \\n\" +\n" +
                "                \"\\t\\t\\tПорядок этих объявлений соответствует порядку атрибутов, отсортированных в алфавитном порядке (см. п.5).\\n\" +\n" +
                "                \"-->\\n\" +\n" +
                "                \"<?xml-stylesheet type=\\\"text/xsl\\\" href=\\\"style.xsl\\\"?>\\n\" +\n" +
                "                \"\\n\" +\n" +
                "                \"<elementOne xmlns=\\\"http://test/1\\\" xmlns:qwe=\\\"http://test/2\\\" xmlns:asd=\\\"http://test/3\\\">\\n\" +\n" +
                "                \"\\t<qwe:elementTwo>\\n\" +\n" +
                "                \"\\t\\t<asd:elementThree xmlns:wer=\\\"http://test/a\\\" xmlns:zxc=\\\"http://test/0\\\" wer:attZ=\\\"zzz\\\" attB=\\\"bbb\\\" attA=\\\"aaa\\\" zxc:attC=\\\"ccc\\\" asd:attD=\\\"ddd\\\" asd:attE=\\\"eee\\\" qwe:attF=\\\"fff\\\"/>\\n\" +\n" +
                "                \"\\t</qwe:elementTwo>  \\n\" +\n" +
                "                \"</elementOne>"
        Assert.assertEquals(example1, example2)
        val etalon1="<ns1:elementOne xmlns:ns1=\"http://test/1\"><ns2:elementTwo xmlns:ns2=\"http://test/2\"><ns3:elementThree xmlns:ns3=\"http://test/3\"><ns1:elementFour> z x c </ns1:elementFour><ns2:elementFive> w w w </ns2:elementFive></ns3:elementThree><ns4:elementSix xmlns:ns4=\"http://test/3\">eee</ns4:elementSix></ns2:elementTwo></ns1:elementOne>"
        val etalon2="<ns1:elementOne xmlns:ns1=\"http://test/1\"><ns2:elementTwo xmlns:ns2=\"http://test/2\"><ns3:elementThree xmlns:ns3=\"http://test/3\" xmlns:ns4=\"http://test/0\" xmlns:ns5=\"http://test/a\" ns4:attC=\"ccc\" ns2:attF=\"fff\" ns3:attD=\"ddd\" ns3:attE=\"eee\" ns5:attZ=\"zzz\" attA=\"aaa\" attB=\"bbb\"></ns3:elementThree></ns2:elementTwo></ns1:elementOne>"
    }

    @Test
    fun process3() {
        val test = transFromSuckers()
        val wr = FileWriter("input.xml")
        val write = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<!-- \n" +
                "\tВсё то же, что в test case 1, плюс правила 4 и 5:\n" +
                "\t- Удалить namespace prefix, которые на текущем уровне объявляются, но не используются.\n" +
                "\t- Проверить, что namespace текущего элемента объявлен либо выше по дереву, либо в текущем элементе. Если не объявлен, объявить в текущем элементе\n" +
                "-->\n" +
                "<?xml-stylesheet type=\"text/xsl\" href=\"style.xsl\"?>\n" +
                "\n" +
                "<elementOne xmlns=\"http://test/1\" xmlns:qwe=\"http://test/2\" xmlns:asd=\"http://test/3\">\n" +
                "\t<qwe:elementTwo>\n" +
                "\t\t<asd:elementThree>\n" +
                "\t\t\t<!-- Проверка обработки default namespace. -->\n" +
                "\t\t\t<elementFour> z x c </elementFour>     \n" +
                "\t\t\t<!-- Тестирование ситуации, когда для одного namespace объявляется несколько префиксов во вложенных элементах. -->\n" +
                "\t\t\t<qqq:elementFive xmlns:qqq=\"http://test/2\"> w w w </qqq:elementFive>\n" +
                "\t\t</asd:elementThree>\n" +
                "\t\t<!-- Ситуация, когда prefix был объявлен выше, чем должно быть в нормальной форме, \n" +
                "\t\tпри нормализации переносится ниже, и это приводит к генерации нескольких префиксов  \n" +
                "\t\tдля одного namespace в sibling элементах. -->\n" +
                "\t\t<asd:elementSix>eee</asd:elementSix>\n" +
                "\t</qwe:elementTwo>  \n" +
                "</elementOne>  "
        wr.write(write)
        wr.close()
        val `in` = FileInputStream("input.xml")
        val out = FileOutputStream("out.xml")
        test.process(`in`, out)
        val contentBuilder = StringBuilder()
        val stream = Files.lines(Paths.get("out.xml"), StandardCharsets.UTF_8)
        run { stream.forEach { s -> contentBuilder.append(s).append("") } }
        val etalon = "<ns1:elementOne xmlns:ns1=\"http://test/1\"><ns2:elementTwo xmlns:ns2=\"http://test/2\"><ns3:elementThree xmlns:ns3=\"http://test/3\"><ns1:elementFour> z x c </ns1:elementFour><ns2:elementFive> w w w </ns2:elementFive></ns3:elementThree><ns4:elementSix xmlns:ns4=\"http://test/3\">eee</ns4:elementSix></ns2:elementTwo></ns1:elementOne>"
        Assert.assertEquals(etalon, contentBuilder.toString())
    }

    @Test
    fun process4() {
        val test = transFromSuckers()
        val wr = FileWriter("input.xml")
        val write = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<!-- \n" +
                "\tВсё то же, что в test case 1, плюс правила 3, 7 и 8:\n" +
                "\t- Атрибуты должны быть отсортированы в алфавитном порядке: сначала по namespace URI (если атрибут - в qualified form), затем – по local name. \n" +
                "\t\tАтрибуты в unqualified form после сортировки идут после атрибутов в qualified form.\n" +
                "\t- Объявления namespace prefix должны находиться перед атрибутами. Объявления префиксов должны быть отсортированы в порядке объявления, а именно:\n" +
                "\t\ta.\tПервым объявляется префикс пространства имен элемента, если он не был объявлен выше по дереву.\n" +
                "\t\tb.\tДальше объявляются префиксы пространств имен атрибутов, если они требуются. \n" +
                "\t\t\tПорядок этих объявлений соответствует порядку атрибутов, отсортированных в алфавитном порядке (см. п.5).\n" +
                "-->\n" +
                "<?xml-stylesheet type=\"text/xsl\" href=\"style.xsl\"?>\n" +
                "\n" +
                "<elementOne xmlns=\"http://test/1\" xmlns:qwe=\"http://test/2\" xmlns:asd=\"http://test/3\">\n" +
                "\t<qwe:elementTwo>\n" +
                "\t\t<asd:elementThree xmlns:wer=\"http://test/a\" xmlns:zxc=\"http://test/0\" wer:attZ=\"zzz\" attB=\"bbb\" attA=\"aaa\" zxc:attC=\"ccc\" asd:attD=\"ddd\" asd:attE=\"eee\" qwe:attF=\"fff\"/>\n" +
                "\t</qwe:elementTwo>  \n" +
                "</elementOne>"
        wr.write(write)
        wr.close()
        val `in` = FileInputStream("input.xml")
        val out = FileOutputStream("out.xml")
        test.process(`in`, out)
        val contentBuilder = StringBuilder()
        val stream = Files.lines(Paths.get("out.xml"), StandardCharsets.UTF_8)
        run { stream.forEach { s -> contentBuilder.append(s).append("") } }
        val etalon = "<ns1:elementOne xmlns:ns1=\"http://test/1\"><ns2:elementTwo xmlns:ns2=\"http://test/2\"><ns3:elementThree xmlns:ns3=\"http://test/3\" xmlns:ns4=\"http://test/0\" xmlns:ns5=\"http://test/a\" ns4:attC=\"ccc\" ns2:attF=\"fff\" ns3:attD=\"ddd\" ns3:attE=\"eee\" ns5:attZ=\"zzz\" attA=\"aaa\" attB=\"bbb\"></ns3:elementThree></ns2:elementTwo></ns1:elementOne>"
        Assert.assertEquals(etalon, contentBuilder.toString())
    }

    @Test
    fun process4testfromstupidGuide() {
        val test = transFromSuckers()
        val wr = FileWriter("input.xml")
        val write = "<ns2:SenderProvidedRequestData xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.0\" Id=\"SIGNED_BY_CONSUMER\">\n" +
                " <MessagePrimaryContent xmlns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.0\">\n" +
                "  <SomeRequest:SomeRequest xmlns:SomeRequest=\"urn://x-artifacts-it-ru/vs/smev/test/test-business-data/1.0\">\n" +
                "   <x xmlns=\"urn://x-artifacts-it-ru/vs/smev/test/test-business-data/1.0\">qweqwe</x>\n" +
                "  </SomeRequest:SomeRequest>\n" +
                " </MessagePrimaryContent>\n" +
                "</ns2:SenderProvidedRequestData>"
        wr.write(write)
        wr.close()
        val `in` = FileInputStream("input.xml")
        val out = FileOutputStream("out.xml")
        test.process(`in`, out)
        val contentBuilder = StringBuilder()
        val stream = Files.lines(Paths.get("out.xml"), StandardCharsets.UTF_8)
        run { stream.forEach { s -> contentBuilder.append(s).append("") } }
        val etalon = "<ns1:SenderProvidedRequestData xmlns:ns1=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.0\" Id=\"SIGNED_BY_CONSUMER\"><ns2:MessagePrimaryContent xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.0\"><ns3:SomeRequest xmlns:ns3=\"urn://x-artifacts-it-ru/vs/smev/test/test-business-data/1.0\"><ns3:x>qweqwe</ns3:x></ns3:SomeRequest></ns2:MessagePrimaryContent></ns1:SenderProvidedRequestData>"
        Assert.assertEquals(etalon, contentBuilder.toString())
    }

    @Test
    fun process4test2fromstupidGuide() {
        val test = transFromSuckers()
        val wr = FileWriter("input.xml")
        val write = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<!-- \n" +
                "\tВсё то же, что в test case 1, плюс правила 3, 7 и 8:\n" +
                "\t- Атрибуты должны быть отсортированы в алфавитном порядке: сначала по namespace URI (если атрибут - в qualified form), затем – по local name. \n" +
                "\t\tАтрибуты в unqualified form после сортировки идут после атрибутов в qualified form.\n" +
                "\t- Объявления namespace prefix должны находиться перед атрибутами. Объявления префиксов должны быть отсортированы в порядке объявления, а именно:\n" +
                "\t\ta.\tПервым объявляется префикс пространства имен элемента, если он не был объявлен выше по дереву.\n" +
                "\t\tb.\tДальше объявляются префиксы пространств имен атрибутов, если они требуются. \n" +
                "\t\t\tПорядок этих объявлений соответствует порядку атрибутов, отсортированных в алфавитном порядке (см. п.5).\n" +
                "-->\n" +
                "<?xml-stylesheet type=\"text/xsl\" href=\"style.xsl\"?>\n" +
                "\n" +
                "<elementOne xmlns=\"http://test/1\" xmlns:qwe=\"http://test/2\" xmlns:asd=\"http://test/3\">\n" +
                "\t<qwe:elementTwo>\n" +
                "\t\t<asd:elementThree xmlns:wer=\"http://test/a\" xmlns:zxc=\"http://test/0\" wer:attZ=\"zzz\" attB=\"bbb\" attA=\"aaa\" zxc:attC=\"ccc\" asd:attD=\"ddd\" asd:attE=\"eee\" qwe:attF=\"fff\"/>\n" +
                "\t</qwe:elementTwo>  \n" +
                "</elementOne>  "
        wr.write(write)
        wr.close()
        val `in` = FileInputStream("input.xml")
        val out = FileOutputStream("out.xml")
        test.process(`in`, out)
        val contentBuilder = StringBuilder()
        val stream = Files.lines(Paths.get("out.xml"), StandardCharsets.UTF_8)
        run { stream.forEach { s -> contentBuilder.append(s).append("") } }
        val etalon = "<ns1:elementOne xmlns:ns1=\"http://test/1\"><ns2:elementTwo xmlns:ns2=\"http://test/2\"><ns3:elementThree xmlns:ns3=\"http://test/3\" xmlns:ns4=\"http://test/0\" xmlns:ns5=\"http://test/a\" ns4:attC=\"ccc\" ns2:attF=\"fff\" ns3:attD=\"ddd\" ns3:attE=\"eee\" ns5:attZ=\"zzz\" attA=\"aaa\" attB=\"bbb\"></ns3:elementThree></ns2:elementTwo></ns1:elementOne>"
        Assert.assertEquals(etalon, contentBuilder.toString())
    }
}