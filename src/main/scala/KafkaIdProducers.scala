import java.util

import akka.actor.ActorSystem
import org.apache.kafka.clients.producer.internals.DefaultPartitioner
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord}

import scala.concurrent.duration._
import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Random
/**
  * Created by valli on 23/01/2017.
  */
object KafkaIdProducers extends App
{
  val system = ActorSystem("MySystem")
  val kafkaProducer = createKafkaProducer()
  system.scheduler.schedule(0 seconds, 5 seconds)(sendRandomIdToKafka(kafkaProducer))
  val r = Random

  def sendRandomIdToKafka(producer: KafkaProducer[String, String]): Unit = {
    val id = r.nextInt(25000)
    val idString = id.toString
    val message=new ProducerRecord[String, String]("invalid-ids-topic",idString,idString)
    producer.send(message)
  }

  def createKafkaProducer(): KafkaProducer[String, String] = {
    val props = new util.HashMap[String, Object]()
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
      "org.apache.kafka.common.serialization.StringSerializer")
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
      "org.apache.kafka.common.serialization.StringSerializer")
    props.put("partitioner.class", "org.apache.kafka.clients.producer.internals.DefaultPartitioner")
    new KafkaProducer[String,String](props)
  }
}