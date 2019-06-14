package jmh.serializers

import akka.serialization.SerializerWithStringManifest
import jmh.models.ModelForMsgpack4z

class Msgpack4zModelSerializer extends SerializerWithStringManifest {
  override def identifier: Int = 779

  override def manifest(o: AnyRef): String = o.getClass.getName

  override def toBinary(o: AnyRef): Array[Byte] = {
    o match {
      case m: ModelForMsgpack4z => ModelForMsgpack4z.serialize(m)
    }
  }

  override def fromBinary(bytes: Array[Byte], manifest: String): AnyRef = {
    manifest match {
      case _ => ModelForMsgpack4z.deserialize(bytes)
    }
  }
}
