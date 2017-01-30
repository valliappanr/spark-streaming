import org.apache.kafka.clients.consumer.ConsumerRecord
import redis.clients.jedis.{Jedis, JedisPool, JedisPoolConfig}

/**
  * Created by valli on 23/01/2017.
  */
case object JedisClientService {
  def createJedisConfig() : JedisPoolConfig = {
    val config = new JedisPoolConfig
    config.setMaxIdle(10)
    config.setMaxWaitMillis(1000 * 10)
    config
  }
  def filterForInValidIds(recordIterator: Iterator[ConsumerRecord[String, String]]): Iterator[ConsumerRecord[String, String]] = {
    val jedisPool = new JedisPool(createJedisConfig, "localhost", 6379, 1000 * 20);
    val jedis = jedisPool.getResource
    val lookupService = new LookupServiceImpl(jedis)
    val filteredRecords = recordIterator.filter( record => isRecordNotExists(lookupService, record.value))
    if (jedis != null) {
      jedis.close
    }
    filteredRecords
  }
  def isRecordNotExists(lookupService: LookupService, key: String): Boolean = {
    lookupService.lookup(key) == null
  }
}