package jmh.serializers

import java.nio.charset.StandardCharsets

import akka.serialization.SerializerWithStringManifest
import jmh.models.ModelForCirce

class CirceSerializer extends SerializerWithStringManifest {

  import io.circe.generic.auto._

  override def identifier: Int = 778

  override def manifest(o: AnyRef): String = o.getClass.getName

  override def toBinary(o: AnyRef): Array[Byte] = {
    o match {
      case m: ModelForCirce => ModelForCirce.serialize(m)
    }
  }

  override def fromBinary(bytes: Array[Byte], manifest: String): AnyRef = {
    manifest match {
      case _ => ModelForCirce.deserialize[ModelForCirce](new String(bytes, StandardCharsets.UTF_8))
    }
  }
}
