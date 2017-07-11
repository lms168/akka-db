package unit1

import akka.actor.ActorSystem
import akka.testkit.TestActorRef
import akka.util.Timeout
import com.akkademy.AkkademyDb
import com.akkademy.message.SetRequest
import org.scalatest.{FunSpecLike, Matchers}

import scala.concurrent.duration._
/**
  * Created by Administrator on 2017/7/11.
  */
class AkkademyDBspec extends FunSpecLike with Matchers{
  implicit val system = ActorSystem()
  implicit val timeout = Timeout(5 seconds)
  describe("akkademyDb") {

    it("should place key/value into map"){
      val actorRef = TestActorRef(new AkkademyDb)
      actorRef ! SetRequest("key", "value")

      val akkademyDb = actorRef.underlyingActor
      akkademyDb.map.get("key").shouldEqual(Some("value"))
    }
  }
}
