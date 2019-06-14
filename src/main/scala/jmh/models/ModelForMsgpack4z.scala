package jmh.models

import java.time.format.DateTimeFormatter
import java.time.{LocalDateTime, OffsetDateTime}

import msgpack4z.CodecInstances.all._
import msgpack4z.MsgpackCodec.codec
import msgpack4z._
import scalaz.{-\/, \/-}

import scala.util.control.NonFatal

case class ModelForMsgpack4z(model: ModelBase = ModelBase())

object ModelForMsgpack4z {

  val factory = new PackerUnpackerFactory {
    def packer = MsgOutBuffer.create()
    def unpacker(bytes: Array[Byte]) = MsgInBuffer(bytes)
  }
  val mapCodecStringKey = CaseMapCodec.string(factory)

  def serialize(model: ModelForMsgpack4z): Array[Byte] =
    MsgpackCodec[ModelForMsgpack4z].toBytes(model, MsgOutBuffer.create())

  def deserialize(bytes: Array[Byte]): ModelForMsgpack4z =
    MsgpackCodec[ModelForMsgpack4z].unpackAndClose(MsgInBuffer(bytes)) match {
      case \/-(value) =>
        value
      case -\/(error) =>
        throw error
    }

  implicit val bigDecimalCodec: MsgpackCodec[BigDecimal] = new MsgpackCodec[BigDecimal] {
    override def pack(packer: MsgPacker, a: BigDecimal): Unit = packer.packString(a.toString)

    override def unpack(unpacker: MsgUnpacker): UnpackResult[BigDecimal] = {
      try {
        \/-(BigDecimal(unpacker.unpackString))
      } catch {
        case NonFatal(e) => -\/(Err(e))
      }
    }
  }

  implicit val localDateTimeCodec: MsgpackCodec[LocalDateTime] = new MsgpackCodec[LocalDateTime] {
    override def pack(packer: MsgPacker, a: LocalDateTime): Unit =
      packer.packString(a.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))

    override def unpack(unpacker: MsgUnpacker): UnpackResult[LocalDateTime] = {
      try {
        \/-(LocalDateTime.parse(unpacker.unpackString, DateTimeFormatter.ISO_LOCAL_DATE_TIME))
      } catch {
        case NonFatal(e) => -\/(Err(e))
      }
    }
  }

  implicit val zonedDateTimeCodec: MsgpackCodec[OffsetDateTime] = new MsgpackCodec[OffsetDateTime] {
    override def pack(packer: MsgPacker, a: OffsetDateTime): Unit =
      packer.packString(a.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME))

    override def unpack(unpacker: MsgUnpacker): UnpackResult[OffsetDateTime] = {
      try {
        \/-(OffsetDateTime.parse(unpacker.unpackString, DateTimeFormatter.ISO_OFFSET_DATE_TIME))
      } catch {
        case NonFatal(e) => -\/(Err(e))
      }
    }
  }

  implicit def seqCodec[A](implicit A: MsgpackCodec[A]): MsgpackCodec[Seq[A]] = codec[Seq[A]](
    { (packer, seq) =>
      packer.packArrayHeader(seq.length)
      var x: Seq[A] = seq
      while (x ne Nil) {
        A.pack(packer, x.head)
        x = x.tail
      }
      packer.arrayEnd()
    },
    unpacker =>
      try {
        val size = unpacker.unpackArrayHeader()
        var list: List[A] = Nil
        var i = 0
        var error: -\/[UnpackError] = null
        while (i < size && error == null) {
          A.unpack(unpacker) match {
            case \/-(a) =>
              list ::= a
            case e @ -\/(_) =>
              error = e
          }
          i += 1
        }
        unpacker.arrayEnd()
        if (error == null)
          \/-(list.reverse)
        else
          error
      } catch {
        case NonFatal(e) =>
          -\/(Err(e))
    }
  )

  implicit val modelBaseCodec: MsgpackCodec[ModelBase] =
    mapCodecStringKey.codec(ModelBase.apply _, ModelBase.unapply _)("a", "b", "c", "d", "e", "f", "g", "h", "i", "j")

  implicit val modelCodec: MsgpackCodec[ModelForMsgpack4z] =
    CaseCodec.codec(ModelForMsgpack4z.apply _, ModelForMsgpack4z.unapply _)
}
