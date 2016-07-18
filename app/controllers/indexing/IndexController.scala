package controllers.indexing

import akka.actor.{ActorSystem, Actor, Props, ActorRef}
import akka.event.Logging
import akka.stream.ActorMaterializer
import akka.util.Timeout
import com.typesafe.config.{Config, ConfigFactory}
import crawlerinformation.{ReindexInfo, SearchInfo}
import play.api.libs.json.{JsResult, Json}
import play.api.mvc.{Action, Controller}
import remotelookup.RemoteLookupProxy
import scala.concurrent.Promise
import akka.pattern.ask
import scala.concurrent.duration._

import scala.concurrent.duration.{FiniteDuration, Duration}
import scala.util.Try
import scala.util.Success
import scala.util.Failure

trait IndexController extends Controller {
  val config = ConfigFactory.load("frontend")

  implicit val system = ActorSystem("frontend", config)
  val log = Logging(system.eventStream, "frontend")
  implicit def executionContext = system.dispatcher
  implicit val materializer = ActorMaterializer()
  implicit val requestTimeout = Timeout(4 seconds)

  def createPath(): String = {
    val config = ConfigFactory.load("frontend").getConfig("backend")
    val host = config.getString("host")
    val port = config.getInt("port")
    val protocol = config.getString("protocol")
    val systemName = config.getString("system")
    val actorName = config.getString("actor")
    val retVal = s"$protocol://$systemName@$host:$port/$actorName"
    println("Created path =" + retVal)
    retVal
  }

  val bakendActor: ActorRef = {
    val path = createPath()
    system.actorOf(Props(new RemoteLookupProxy(path)), "lookupBoxOffice")
  }

  implicit val userReads = Json.reads[SearchInfo]
  implicit val userWrites = Json.writes[SearchInfo]

  implicit val userReadsIndex = Json.reads[ReindexInfo]
  implicit val userWritesIndex = Json.writes[ReindexInfo]

  def stratCrawl = Action.async{

    val prom = Promise[String]
    prom.success("Good Job")

    prom.future.map(response =>Ok(response))
  }

  case class CreateEvent(name: String, tickets: Int)

  def search = Action.async(parse.json) { request =>
    val t: JsResult[SearchInfo] = request.body.validate[SearchInfo]
    val prom = Promise[String]

    parseData(t) match{
      case Right(s:SearchInfo) => {
        println("Here before asking")
        bakendActor.ask(CreateEvent("SJM",3)).onComplete(xs => {
          xs match{
            case Success(v) => prom.success(v.asInstanceOf[String])
            case Failure(e) => prom.success("System is not ready")
          }
        })

        prom.future.map(response =>Ok(response))
      }
      case Left(x:String) => {
        prom.success("Bad Search, could not convert the input")
        prom.future.map(response =>BadRequest(response))
      }
    }
  }

  def reIndex = Action.async(parse.json) { request =>
    val t: JsResult[ReindexInfo] = request.body.validate[ReindexInfo]
    val prom = Promise[String]

    parseData(t) match{
      case Right(s:ReindexInfo) => {
        prom.success("Good Job Search " + s.url + ", "+ s.alreadyDone +" and " + s.search_item)
        prom.future.map(response =>Ok(response))
      }
      case Left(x:String) => {
        prom.success("Bad Search, could not convert the input")
        prom.future.map(response =>BadRequest(response))
      }
    }
  }

  private def parseData[T](x:JsResult[T]):Either[String,T]={
    x.fold(
      invalid = e => Left(e.mkString),
      valid   = T => Right(T)
    )
  }

}
