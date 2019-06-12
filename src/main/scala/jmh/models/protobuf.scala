package jmh.models

import java.time.format.DateTimeFormatter
import java.time.{LocalDateTime, OffsetDateTime}

import scalapb.TypeMapper

package object protobuf {

  implicit val bigDecimalFromStringMapper =
    TypeMapper[String, BigDecimal](s => if (s.isEmpty) BigDecimal(0) else BigDecimal(s))(_.toString())

  implicit val bigDecimalFromIntMapper =
    TypeMapper[Int, BigDecimal](BigDecimal(_))(_.toInt)

  implicit val bigDecimalFromDoubleMapper =
    TypeMapper[Double, BigDecimal](BigDecimal.valueOf)(_.toDouble)

  implicit val localDateTimeMapper =
    TypeMapper[String, LocalDateTime](s => {
      if (s.isEmpty) LocalDateTime.MIN
      else LocalDateTime.parse(s, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
    })(_.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))

  implicit val offsetDateTimeMapper =
    TypeMapper[String, OffsetDateTime](s => {
      if (s.isEmpty) OffsetDateTime.MIN
      else OffsetDateTime.parse(s, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
    })(_.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME))
}
