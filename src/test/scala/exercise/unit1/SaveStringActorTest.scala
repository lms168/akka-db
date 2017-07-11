package exercise.unit1

import akka.actor.ActorSystem
import akka.testkit.TestActorRef
import com.exercise.unit1.SaveStringActor
import org.scalatest.{FunSpec, FunSpecLike, Matchers, ShouldMatchers}

/**
  * Created by Administrator on 2017/7/12.
  */
class SaveStringActorTest extends FunSpec with ShouldMatchers {
  implicit  val system = ActorSystem()
  describe("SaveStringActor"){
    describe("given msg"){
      it("should place msg into Seq"){
        val actorRef = TestActorRef(new SaveStringActor)

        actorRef ! "abc"

        val saveStringActor = actorRef.underlyingActor

        saveStringActor.msgSeq should contain value ("abc")
    }
    }
  }

}
