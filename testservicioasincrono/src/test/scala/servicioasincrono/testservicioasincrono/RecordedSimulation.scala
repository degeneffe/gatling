package servicioasincrono.testservicioasincrono

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._
import scala.util.Random

class RecordedSimulation extends Simulation {

	val httpProtocol = http
		.baseURL("http://127.0.0.1:8080")
		.inferHtmlResources()
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
		.acceptEncodingHeader("gzip, deflate, sdch")
		.acceptLanguageHeader("es-ES,es;q=0.8,en;q=0.6")
		.userAgentHeader("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.92 Safari/537.36")

		//val feeder = Iterator.continually(Map("id" -> (Random.alphanumeric.take(20).mkString)))
		val feeder = Iterator.continually(Map("id" -> (Random.nextInt(99999999))))
		
		
	val headers_0 = Map(
		"Cache-Control" -> "max-age=0",
		"Upgrade-Insecure-Requests" -> "1")

    val uri1 = "127.0.0.1"

	val scn = scenario("RecordedSimulation").feed(feeder)
		.exec(http("request_0")
			.post("/orderid")
			.body(StringBody("""{  "id": "${id}",  "orderID": "prueba asíncrona2"}""")).asJSON
			.check(status.is(200)))

	setUp(scn.inject(rampUsers(10000) over (100 seconds))).protocols(httpProtocol)
}