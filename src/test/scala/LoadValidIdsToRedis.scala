import com.redis.RedisClient

/**
  * Created by valli on 23/01/2017.
  */
object LoadValidIdsToRedis extends App {
  val r = new RedisClient("localhost", 6379)
  val stream = (1 to 10000).toStream
  stream.foreach(data => {
    val dataString = data.toString
    r.set(dataString, dataString)
  })
}