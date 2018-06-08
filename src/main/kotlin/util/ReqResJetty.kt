package util
import org.eclipse.jetty.client.HttpClient
import java.nio.file.Paths
import java.io.FileOutputStream
import java.io.FileWriter
import java.nio.file.Files
import java.nio.file.Path

class ReqResJetty constructor(url:String) {
    internal val httpClient=HttpClient()
    internal val url = url
    fun funl(){
        val wr = FileWriter("test")
        wr.write("bombombomтест")
        wr.close()
    }
    fun exchange(req:String, res:String){
        httpClient.start()
        var response = httpClient
            .POST(this.url)
            .file(Paths.get(req), "text/xml")
            .send()
        val reset=FileWriter(res)
        reset.close()
        print(response.contentAsString)
        FileOutputStream(res).use { fos ->
        fos.write(response.content)
    }


}
}