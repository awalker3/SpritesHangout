package actors

import akka.actor.Actor
import akka.actor.ActorRef
import akka.actor.Props
import _root_.actors.actors.SpriteManager

class SpriteActor(out: ActorRef, manager: ActorRef) extends Actor {
    manager ! SpriteManager.NewSprite(self)
    manager ! SpriteManager.SendMessage(self)
    import SpriteActor._
    def receive = {
        case (a,b,c,d) => println("wut")
        case DrawSprite(x, y, size, color) => println(s"Gotta draw $x $y $size $color")
        case m => println("Unhandled message in SpriteActor: " + m)
    }
}

object SpriteActor {
    def props(out: ActorRef, manager: ActorRef) = Props(new SpriteActor(out, manager))
    case class DrawSprite(x: Any, y: Any, size: Any, color: Any)
}

