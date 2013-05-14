package calendar

import mcp.ical.iCal
import mcp.scala.implicits.FileImplicits._

object FromStringToGCal {

  /**
   * extract dates from a String and create google calendar entries for them
   */
  def main(args: Array[String]) {

    val raw_dates = """11.01.2013, 08.02.2013, 28.02.2013, 22.03.2013, 12.04.2013,
10.05.2013, 06.06.2013, 04.07.2013, 25.07.2013, 05.09.2013,
27.09.2013, 17.10.2013, 07.11.2013, 28.11.2013, 19.12.2013"""

      /*
       * extract dates
       */
      def dateRX = """[0-9]{1,2}\.[0-9]{1,2}\.[0-9]{4}""".r

    // Anmerkung: Elegant wäre eine Möglichkeit aus spezifischen RegExs automatisch eine Oberklasse zu lernen/abzuleiten. Trivial wäre natürlich die Vereinigung möglich.

    val results = dateRX.findAllIn(raw_dates)

    for (result ← results) print(result)

    /* 
     * TODO create ICal solution
     */

    val cal = iCal.create("-//Hello World Calendar//iCal4j 1.0//EN")
    cal.getComponents.add(iCal.createOneHourMeeting)

    iCal.saveCalender(cal, """c:\tmp\myFirstCalender.ics""")

    /* 
     * TODO create GCal entries 
     */

    /*
    val postUrl = new URL("https://www.google.com/calendar/feeds/jo@gmail.com/private/full")

    val client = new Calendar("yourCo-yourAppName-v1"); //TODO
    client.setUserCredentials("jens.wissmann@gmail.com", "W3haogec"); 

    val event = new Event

    event.setSummary("Tennis with Beth")

    val startTime = new DateTime("2013-01-31T15:00:00+01:00")
    val endTime = new DateTime("2013-01-31T17:00:00+01:00")

      implicit def mkEDT(dt: DateTime): EventDateTime = { val edt = new EventDateTime(); edt.setDate(dt); edt }

    event.setStart(startTime)
    event.setStart(endTime)

    // Send the request and receive the response:

    val insertedEntry = client.insert(postUrl, myEntry);

    */
  }

}