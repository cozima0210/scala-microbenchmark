package jmh.models

import java.time.{LocalDateTime, OffsetDateTime}

import jmh.models.protobuf.ModelForPB

object ModelForPBFactory {
  def default: ModelForPB = {
    val decimals: Seq[BigDecimal] = Seq(
      111.1111111111111, 111.2222222222222, 222.2222222222222, 222.3333333333333, 333.3333333333333, 333.4444444444444,
      444.4444444444444, 444.5555555555555, 555.5555555555555, 555.6666666666666, 666.6666666666666, 666.7777777777777,
      777.7777777777777, 777.8888888888888, 888.8888888888888, 888.9999999999999, 999.9999999999999, 999.0000000000000
    )

    ModelForPB(
      a = true,
      b = 1.toShort,
      c = 1,
      d = 1.toLong,
      e = BigDecimal(1),
      f = "1",
      g = false,
      h = LocalDateTime.now(),
      i = OffsetDateTime.now(),
      j = decimals
    )
  }
}
