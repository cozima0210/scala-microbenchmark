package jmh.models

import io.circe.parser._
import io.circe.syntax._

case class ModelForCirce(model: ModelBase = ModelBase())

object ModelForCirce {
  def serialize[A:   io.circe.Encoder](a: A):      Array[Byte] = a.asJson.noSpaces.getBytes
  def deserialize[A: io.circe.Decoder](s: String): A           = decode[A](s).right.get
}
