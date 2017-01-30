import java.text.SimpleDateFormat
import java.util.Date

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.spark.rdd.RDD
import org.elasticsearch.spark._

/**
  * Created by valli on 23/01/2017.
  */

case object ElasticStore {
  val dateFormat  = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
  val invalidCardIndex = "spark/invalid-cards"

  def storeInES(rdd: RDD[ConsumerRecord[String, String]]): Unit = {
    rdd.map{ record => {
      val timestamp = dateFormat.format(new Date())
      val missingValue = record.value
      Map("@timestamp" -> timestamp, "invalidCard" -> missingValue)
    }
  }.saveToEs(invalidCardIndex)
  }
}