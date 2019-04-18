package jmh.models

import java.time._

case class ModelBase(
  a: Boolean        = true,
  b: Short          = 1.toShort,
  c: Int            = 1,
  d: Long           = 1.toLong,
  e: BigDecimal     = BigDecimal(1),
  f: String         = "1",
  g: Boolean        = false,
  h: LocalDateTime  = LocalDateTime.now(),
  i: OffsetDateTime = OffsetDateTime.now(),
  j: Seq[BigDecimal] = Seq(
    111.1111111111111, 111.2222222222222, 222.2222222222222, 222.3333333333333, 333.3333333333333, 333.4444444444444,
    444.4444444444444, 444.5555555555555, 555.5555555555555, 555.6666666666666, 666.6666666666666, 666.7777777777777,
    777.7777777777777, 777.8888888888888, 888.8888888888888, 888.9999999999999, 999.9999999999999, 999.0000000000000
  )
)
