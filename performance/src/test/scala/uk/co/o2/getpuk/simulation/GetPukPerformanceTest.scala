package uk.co.o2.getpuk.simulation

import io.gatling.core.Predef._
import uk.co.o2.getpuk.model.Puk._

import scala.concurrent.duration._

class GetPukPerformanceTest extends GetPukBaseSimulation {

  val getPukCompletePerformanceScenario = scenario("GetPuk System Performance").during(testRuntimeDurationInSeconds seconds) {
    randomSwitch(
      100.0 -> exec(pukExecutions)
    )
  }

  if (requestsPerSecond > 0) {
    setUp(getPukCompletePerformanceScenario.inject(atOnceUsers(requestsPerSecond)))
      .throttle(jumpToRps(requestsPerSecond.toInt), holdFor(testRuntimeDurationInSeconds seconds))
      .protocols(httpConfiguration)
      .assertions(global.successfulRequests.percent.greaterThan(99))
  } else if (noOfUsers > 0) {
    setUp(getPukCompletePerformanceScenario.inject(rampUsers(noOfUsers) over (usersRampUpValInSeconds seconds)))
      .protocols(httpConfiguration)
      .assertions(global.successfulRequests.percent.greaterThan(99))
  }

}