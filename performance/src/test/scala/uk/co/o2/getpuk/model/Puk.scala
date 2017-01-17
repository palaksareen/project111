package uk.co.o2.getpuk.model

import java.util.UUID

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import uk.co.o2.getpuk.feeder.PukDataFeeder._

object Puk {

//  val setPukRequestBody =
//    """
//      |{
//      |  "mpn": "${mpn}"
//      |}
//    """.stripMargin

  val setPukWith07Input = exec(http("Get PUK with mpn starting with 07")
    .post("/")
    .headers(Map("content-type" -> "application/x-www-form-urlencoded"))
    .formParam("mpn" , "${mpn}")   
    .check(
      status.is(200)
    )
  )

  val setPukWith44Input = exec(http("Get PUK with mpn starting with 44")
    .post("/")
    .headers(Map("content-type" -> "application/x-www-form-urlencoded"))
    .formParam("mpn" , "${mpn}")    
    .check(
      status.is(200)
    )
  )

  val pukExecutions = group("Puk"){
    roundRobinSwitch(
        pukFeederWith44.exec(setPukWith44Input),
        pukFeederWith07.exec(setPukWith07Input)
      )
  }
}
