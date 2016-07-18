package services.user

import akka.actor.{ActorRef, Props, Actor}

/**
  * Created by AnandKrishnan on 6/6/2016.
  */
class ReverseMaster extends Actor {
 // val storeActor = context.actorOf(Props(new StoreActor()))
  val revSet:scala.collection.mutable.Set[String]=scala.collection.mutable.Set()
  def receive = {
    case x:String =>{
      println("From ReverseMaster " + x)
      val curActor = context.actorOf(Props(new ReverseActor))
      curActor ! (sender, x)
    }
    case (b:ActorRef,xVal:String) => {
      revSet += (xVal)
      //val retVal = revSet.toList.foldLeft(", "){(x,y) => x+y}
      b ! ReverseActorMessage.ReversedSet(revSet.toList)
    }
  }
}
