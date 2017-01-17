package uk.co.o2.getpuk.feeder

import io.gatling.core.Predef._

object PukDataFeeder {


  var installMsisdnStartIndex = 7704610258L


  def getNextInstallMsisdn(): Long ={
    installMsisdnStartIndex = installMsisdnStartIndex + 1
    return installMsisdnStartIndex
  }

  val installFirstFeeder = Iterator.continually(Map(
    "mpn" -> ("44"+getNextInstallMsisdn())
  ))

  val pukFeederWith44 = feed(installFirstFeeder)
  
  
  val installSecondFeeder = Iterator.continually(Map(
    "mpn" -> ("0"+getNextInstallMsisdn())
  ))
  
  val pukFeederWith07 = feed(installSecondFeeder)

  
}
