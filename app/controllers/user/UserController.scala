package controllers.user

import akka.actor.{Actor, ActorSystem, Props, actorRef2Scala}
import akka.util.Timeout
import play.api.libs.json._
import play.api.mvc._
import services.user._

import scala.concurrent.Promise
import scala.concurrent.duration._

class UserController extends Controller {
  implicit val actorSystem = ActorSystem("test")
  implicit def executionCext = actorSystem.dispatcher
  val reverseMaster = actorSystem.actorOf(Props[ReverseMaster], name = "reversemaster")
  implicit val timeout = Timeout(10 seconds)

  def reverseIt(txt: String) = Action.async {
    println("The string to reverse " + txt)
    val prom = Promise[List[String]]

    val replyTo = actorSystem.actorOf(Props(new Actor {
      def receive = {
        case ReverseActorMessage.ReversedSet(reply) =>
          println("Received bak" + reply)
          prom.success(reply)
          context.stop(self)
      }

      reverseMaster ! txt
    }))

    prom.future.map(response => Ok(Json.toJson(response.zipWithIndex map { t => (t._2.toString, t._1) } toMap)))
  }
}

    