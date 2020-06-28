package utils

import java.util.Date

trait DTDate {
  def date(y: Int, m: Int, d: Int): Date = new Date(y, m, d)
  def now: Date = new Date
  def nowToString: String = {
    val now = new Date
    s"${now.getYear}${now.getMonth}${now.getDate}-${now.getHours}:${now.getMinutes}:${now.getSeconds}"
  }
}

object DTDate extends DTDate

trait UsesDate {
  val date: DTDate
}

trait MixInDate {
  val date: DTDate = DTDate
}
