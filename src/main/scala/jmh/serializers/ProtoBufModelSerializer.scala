package jmh.serializers

import akka.serialization.SerializerWithStringManifest
import jmh.models.protobuf.ModelForPB

class ProtoBufModelSerializer extends SerializerWithStringManifest {
  override def identifier: Int = 777

  override def manifest(o: AnyRef): String = o.getClass.getName

  override def toBinary(o: AnyRef): Array[Byte] = {
    o match {
      case m: ModelForPB => m.toByteArray
    }
  }

  override def fromBinary(bytes: Array[Byte], manifest: String): AnyRef = {
    manifest match {
      case _ => ModelForPB.parseFrom(bytes)
    }
  }
}
