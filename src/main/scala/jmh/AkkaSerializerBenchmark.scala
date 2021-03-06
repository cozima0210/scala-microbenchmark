package jmh

import java.util.concurrent.TimeUnit

import akka.actor.ActorSystem
import akka.serialization.{Serialization, SerializationExtension, Serializer}
import jmh.models.{ModelForJava, ModelForKryo}
import org.openjdk.jmh.annotations._

@State(Scope.Thread)
class AkkaSerializerBenchmark {

  val system:        ActorSystem   = ActorSystem()
  val serialization: Serialization = SerializationExtension(system)

  val modelForJava: ModelForJava = ModelForJava()
  val modelForKryo: ModelForKryo = ModelForKryo()

  val serializerForJava: Serializer = serialization.findSerializerFor(modelForJava)
  val serializerForKryo: Serializer = serialization.findSerializerFor(modelForKryo)

  val bytesForJava: Array[Byte] = serializerForJava.toBinary(modelForJava)
  val bytesForKryo: Array[Byte] = serializerForKryo.toBinary(modelForKryo)

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
  def javaDeserializerBenchmark(): Unit = {
    serializerForJava.fromBinary(bytesForJava)
  }

  @Benchmark
  @BenchmarkMode(Array(Mode.Throughput, Mode.AverageTime))
  @OutputTimeUnit(TimeUnit.MILLISECONDS)
  def kryoDeserializerBenchmark(): Unit = {
    serializerForKryo.fromBinary(bytesForKryo)
  }

}
