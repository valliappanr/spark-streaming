import redis.clients.jedis.Jedis

/**
  * Created by valli on 24/01/2017.
  */
trait LookupService extends Serializable{
  def lookup(key: String) : String
}

class LookupServiceImpl(client: Jedis) extends LookupService {
  override def lookup(key: String): String = {
    client.get(key)
  }
}