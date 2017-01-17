package uk.co.o2.getpuk.simulation

import io.gatling.core.scenario.Simulation
import io.gatling.http.Predef._

class GetPukBaseSimulation extends Simulation{
  val getPukUrl = System.getenv("getPukUrl")
  val noOfUsers = Integer.valueOf(System.getenv("noOfUsers"))
  val usersRampUpValInSeconds = System.getenv("usersRampUpValInSeconds").toLong
  val testRuntimeDurationInSeconds = System.getenv("testRuntimeDurationInSeconds").toLong
  val requestsPerSecond =System.getenv("rps").toInt

  val httpConfiguration = http.baseURL(getPukUrl)
}