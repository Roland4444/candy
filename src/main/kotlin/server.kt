import spark.Request
import spark.Spark
import spark.Spark.*
import util.timeBasedUUID
object server {
    @JvmStatic
    fun main(args: Array<String>) {
        Spark.port(10000)
        get("/") {req,res ->
            val t = timeBasedUUID()
            print(t.generate())
            t.generate()
            }
        get("/soap") {req,res ->
        }

        post("/") {req,res ->
            res.body()
        }
        class RequestStub : Request()

    }
}