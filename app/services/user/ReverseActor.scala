package services.user

import akka.actor.{PoisonPill, ActorRef, Actor}

/**
  * Created by AnandKrishnan on 6/6/2016.
  */
class ReverseActor extends Actor {
  def receive = {
    case (a:ActorRef, x: String) => {
      println("From ReverseActor " + x)
      val reversed = reverseIt(x)
      println("Reversed Val " + reversed)
      sender ! (a,reversed)
    }
  }

  def reverseIt(str:String):String ={
    def rev(accum:String, cur:String):String={
      cur.length() match {
        case 0 => accum
        case _ => rev(cur.head+accum,cur.tail)
      }
    }
    rev("", str)
  }
}
