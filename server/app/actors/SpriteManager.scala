package actors

package actors

import akka.actor.Actor
import akka.actor.ActorRef

class SpriteManager extends Actor {
    private var sprites = List.empty[ActorRef]

    import SpriteManager._
    def receive = {
        case NewSprite(sprite) => sprites ::= sprite
        case SendMessage(dest) => dest ! SpriteActor.DrawSprite(100, 100, 50, "ahh")
        case m => println("Unhandled message in SpriteManager: " + m)
    }
}

object SpriteManager {
    case class NewSprite(sprite: ActorRef)
    case class SendMessage(sprite: ActorRef)
}