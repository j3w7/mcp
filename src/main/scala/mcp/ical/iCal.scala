package mcp.ical

import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.OutputStream
import java.net.URI
import java.util.GregorianCalendar
import mcp.scala.implicits.FileImplicits.newFileInputStream
import mcp.scala.implicits.FileImplicits.newFileOutputStream
import net.fortuna.ical4j.data.CalendarBuilder
import net.fortuna.ical4j.data.CalendarOutputter
import net.fortuna.ical4j.model.Calendar
import net.fortuna.ical4j.model.Date
import net.fortuna.ical4j.model.DateTime
import net.fortuna.ical4j.model.ParameterList
import net.fortuna.ical4j.model.TimeZone
import net.fortuna.ical4j.model.TimeZoneRegistryFactory
import net.fortuna.ical4j.model.component.VEvent
import net.fortuna.ical4j.model.component.VTimeZone
import net.fortuna.ical4j.model.parameter.Cn
import net.fortuna.ical4j.model.parameter.Encoding
import net.fortuna.ical4j.model.parameter.Role
import net.fortuna.ical4j.model.parameter.Value
import net.fortuna.ical4j.model.property.Attach
import net.fortuna.ical4j.model.property.Attendee
import net.fortuna.ical4j.model.property.CalScale
import net.fortuna.ical4j.model.property.ProdId
import net.fortuna.ical4j.model.property.Version
import net.fortuna.ical4j.util.UidGenerator

object iCal {

  def parse(f: File) = new CalendarBuilder build f
  def parse(s: String) = new CalendarBuilder build s

  def create(id: String): Calendar = {
    val calendar = new Calendar
    calendar.getProperties.add(new ProdId(id))
    calendar.getProperties.add(Version.VERSION_2_0)
    calendar.getProperties.add(CalScale.GREGORIAN)
    return calendar
  }

  def date(y: Int, m: Int, d: Int) = {
    val cal = new GregorianCalendar
    cal setTimeZone new TimeZone(new VTimeZone)
    cal set (y, m, d)
    new Date(cal.getTime)
  }

  // TODO nachvollziehen und vereinfachen
  def createAllDayEvent: VEvent = {

    // initialise as an all-day event..
    val event = new VEvent(date(2012, 8, 28), "Christmas Day")

    // Generate a UID for the event..
    val ug = new UidGenerator("1")
    event.getProperties.add(ug.generateUid)

    return event
  }

  //TODO vereinfachen und modularisieren
  def createOneHourMeeting: VEvent = {

    // Create a TimeZone
    val registry = TimeZoneRegistryFactory.getInstance.createRegistry
    val timezone = registry.getTimeZone("America/Mexico_City")
    val tz = timezone.getVTimeZone

    // Start Date is on: April 1, 2008, 9:00 am
    val startDate = new GregorianCalendar(2008, 1, 1, 9, 0)
    startDate.setTimeZone(timezone)
    // End Date is on: April 1, 2008, 13:00
    val endDate = new GregorianCalendar(2008, 1, 13, 13, 0)
    endDate.setTimeZone(timezone)

    // Create the event
    val eventName = "Progress Meeting"
    val start = new DateTime(startDate.getTime)
    val end = new DateTime(endDate.getTime)
    val meeting = new VEvent(start, end, eventName)

    // add timezone info..
    meeting.getProperties.add(tz.getTimeZoneId)

    // generate unique identifier..
    val ug = new UidGenerator("uidGen")
    val uid = ug.generateUid
    meeting.getProperties.add(uid)

    // add attendees..
    val dev1 = new Attendee(URI.create("mailto:dev1@mycompany.com"))
    dev1.getParameters.add(Role.REQ_PARTICIPANT)
    dev1.getParameters.add(new Cn("Developer 1"))
    meeting.getProperties.add(dev1)

    val dev2 = new Attendee(URI.create("mailto:dev2@mycompany.com"))
    dev2.getParameters.add(Role.OPT_PARTICIPANT)
    dev2.getParameters.add(new Cn("Developer 2"))
    meeting.getProperties.add(dev2)

    return meeting
  }

  def saveCalender: (Calendar, OutputStream) ⇒ Unit = new CalendarOutputter output (_, _)

  def attachBinaryData: Unit = {
    val input = new FileInputStream("etc/artwork/logo.png")
    val output = new ByteArrayOutputStream

    Iterator
      .continually(input.read)
      .takeWhile(-1 !=)
      .foreach(output.write)

    val params = new ParameterList
    params.add(Value.BINARY)
    params.add(Encoding.BASE64)

    val attach = new Attach(params, output.toByteArray)
  }

  // TODO move to TestCase
  def main(args: Array[String]): Unit = {

    val cal = iCal.create("-//Hello World Calendar//iCal4j 1.0//EN")
    cal.getComponents.add(createAllDayEvent)
    cal.getComponents.add(createOneHourMeeting)

    saveCalender(cal, """c:\tmp\myFirstCalender.ics""")
  }

}