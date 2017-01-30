import org.scalatest._
import org.scalamock._
import org.scalamock.scalatest._
import redis.clients.jedis.Jedis

import JedisClientService._
/**
  * Created by valli on 23/01/2017.
  */
class JedisClientServiceTest extends FeatureSpec with Matchers with GivenWhenThen with MockFactory {

  val validKey = "key"
  val invalidKey = "invalid-key"
  val validValue = "4"
  val lookupService = mock[LookupService]

  feature("Test lookup service for lookup existence ") {
    scenario("lookup service should validate the existence of records correctly") {
      Given("lookup service with default values")
      (lookupService.lookup _).expects(validKey).returning(validValue)
      When("passing the existing key")
      val value = isRecordNotExists(lookupService, validKey)
      Then("value should be false")
      value shouldBe false
    }
    scenario("jedis client service should validate correctly the inexistetnce of records correctly") {
      Given("lookup service with predefined values")
      (lookupService.lookup _).expects(invalidKey).returning(null)
      When("passing the non-existing key")
      val value = isRecordNotExists(lookupService, invalidKey)
      Then("value should be true")
      value shouldBe true
    }
  }


}
