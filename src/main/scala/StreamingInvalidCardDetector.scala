import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.InputDStream
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010._
import org.apache.spark.streaming.{Seconds, StreamingContext}
import JedisClientService._
import ElasticStore._
import org.apache.spark.sql.SparkSession
/**
  * Created by valli on 22/09/2016.
  */
object StreamingInvalidCardDetector {

  def createKafkaParams(): Map[String, Object] = {

    Map[String, Object](
      "bootstrap.servers" -> "localhost:9092,anotherhost:9092",
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      "group.id" -> "use_a_separate_group_id_for_each_stream",
      "auto.offset.reset" -> "latest",
      "enable.auto.commit" -> (false: java.lang.Boolean)
    )
  }

  def createStreamingContext(kafkaParams: Map[String, Object], topics: Array[String], ssc: StreamingContext): InputDStream[ConsumerRecord[String, String]] = {

    KafkaUtils.createDirectStream[String, String](
      ssc,
      PreferConsistent,
      Subscribe[String, String](topics, kafkaParams)
    )
  }

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().appName("invalid-id-processor")
        .config("es.index.auto.create", "true").getOrCreate()
    val ssc = new StreamingContext(spark.sparkContext, Seconds(10))
    val stream = createStreamingContext(createKafkaParams(), Array("invalid-ids-topic"), ssc)
    stream.foreachRDD { rdd => {
      val result = rdd.mapPartitions(recordIterator => {
        filterForInValidIds(recordIterator)
      }
      )
      storeInES(result)
    }
    }
    ssc.start
    ssc.awaitTermination()
  }
}