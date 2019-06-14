package jmh

import java.util.concurrent.TimeUnit

import akka.actor.ActorSystem
import akka.serialization.{Serialization, SerializationExtension, Serializer}
import jmh.models.protobuf.ModelForPB
import jmh.models.{ModelForCirce, ModelForJava, ModelForKryo, ModelForMsgpack4z, ModelForPBFactory}
import org.openjdk.jmh.annotations._

@State(Scope.Thread)
class AkkaSerializerBenchmark {

  val system:        ActorSystem   = ActorSystem()
  val serialization: Serialization = SerializationExtension(system)

  val modelForJava:      ModelForJava      = ModelForJava()
  val modelForKryo:      ModelForKryo      = ModelForKryo()
  val modelForPB:        ModelForPB        = ModelForPBFactory.default
  val modelForCirce:     ModelForCirce     = ModelForCirce()
  val modelForMsgpack4z: ModelForMsgpack4z = ModelForMsgpack4z()

  val serializerForJava:      Serializer = serialization.findSerializerFor(modelForJava)
  val serializerForKryo:      Serializer = serialization.findSerializerFor(modelForKryo)
  val serializerForPB:        Serializer = serialization.findSerializerFor(modelForPB)
  val serializerForCirce:     Serializer = serialization.findSerializerFor(modelForCirce)
  val serializerForMsgpack4z: Serializer = serialization.findSerializerFor(modelForMsgpack4z)

  val bytesForJava:      Array[Byte] = serializerForJava.toBinary(modelForJava)
  val bytesForKryo:      Array[Byte] = serializerForKryo.toBinary(modelForKryo)
  val bytesForPB:        Array[Byte] = serializerForPB.toBinary(modelForPB)
  val bytesForCirce:     Array[Byte] = serializerForCirce.toBinary(modelForCirce)
  val bytesForMsgpack4z: Array[Byte] = serializerForMsgpack4z.toBinary(modelForMsgpack4z)

  @Setup
  def setup(): Unit = {}

  @TearDown
  def tearDown(): Unit = {
    system.terminate()
  }

  @Benchmark
  @BenchmarkMode(Array(Mode.Throughput, Mode.AverageTime))
  @OutputTimeUnit(TimeUnit.MILLISECONDS)
  def javaSerializerBenchmark(): Unit = {
    serializerForJava.toBinary(modelForJava)
  }

  @Benchmark
  @BenchmarkMode(Array(Mode.Throughput, Mode.AverageTime))
  @OutputTimeUnit(TimeUnit.MILLISECONDS)
  def kryoSerializerBenchmark(): Unit = {
    serializerForKryo.toBinary(modelForKryo)
  }

  @Benchmark
  @BenchmarkMode(Array(Mode.Throughput, Mode.AverageTime))
  @OutputTimeUnit(TimeUnit.MILLISECONDS)
  def pbSerializerBenchmark(): Unit = {
    serializerForPB.toBinary(modelForPB)
  }

  @Benchmark
  @BenchmarkMode(Array(Mode.Throughput, Mode.AverageTime))
  @OutputTimeUnit(TimeUnit.MILLISECONDS)
  def circeSerializerBenchmark(): Unit = {
    serializerForCirce.toBinary(modelForCirce)
  }

  @Benchmark
  @BenchmarkMode(Array(Mode.Throughput, Mode.AverageTime))
  @OutputTimeUnit(TimeUnit.MILLISECONDS)
  def msgpackSerializerBenchmark(): Unit = {
    serializerForMsgpack4z.toBinary(modelForMsgpack4z)
  }

  @Benchmark
  @BenchmarkMode(Array(Mode.Throughput, Mode.AverageTime))
  @OutputTimeUnit(TimeUnit.MILLISECONDS)
  def javaDeserializerBenchmark(): Unit = {
    serializerForJava.fromBinary(bytesForJava)
  }

  @Benchmark
  @BenchmarkMode(Array(Mode.Throughput, Mode.AverageTime))
  @OutputTimeUnit(TimeUnit.MILLISECONDS)
  def kryoDeserializerBenchmark(): Unit = {
    serializerForKryo.fromBinary(bytesForKryo)
  }

  @Benchmark
  @BenchmarkMode(Array(Mode.Throughput, Mode.AverageTime))
  @OutputTimeUnit(TimeUnit.MILLISECONDS)
  def pbDeserializerBenchmark(): Unit = {
    serializerForPB.fromBinary(bytesForPB)
  }

  @Benchmark
  @BenchmarkMode(Array(Mode.Throughput, Mode.AverageTime))
  @OutputTimeUnit(TimeUnit.MILLISECONDS)
  def circeDeserializerBenchmark(): Unit = {
    serializerForCirce.fromBinary(bytesForCirce)
  }

  @Benchmark
  @BenchmarkMode(Array(Mode.Throughput, Mode.AverageTime))
  @OutputTimeUnit(TimeUnit.MILLISECONDS)
  def msgpackDeserializerBenchmark(): Unit = {
    serializerForMsgpack4z.fromBinary(bytesForMsgpack4z)
  }

}
