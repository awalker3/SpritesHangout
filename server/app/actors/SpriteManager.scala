package actors

package actors

import akka.actor.Actor
import akka.actor.ActorRef

class SpriteManager extends Actor {
    private var sprites = List.empty[ActorRef]
    private var spritesToDraw = Map.empty[ActorRef, (Int, Int, String)]

    import SpriteManager._
    def receive = {
        case NewSprite(sprite, x, y, color) => {
            sprites ::= sprite
            spritesToDraw = spritesToDraw + (sprite -> (x, y, color))
        }
        case SpriteLocation(dest, x, y, color) => {
            updateLoc(dest, x, y, color)
            val s = spritesToDraw.values.toList
            dest ! SpriteActor.DrawSprite(s)
        }
        case m => println("Unhandled message in SpriteManager: " + m)
    }

    def updateLoc(dest: ActorRef, x: Int, y: Int, color: String): Unit = {
        val tup = (x, y, color)
        spritesToDraw = spritesToDraw + (dest -> tup)
    }
}

object SpriteManager {
    case class NewSprite(sprite: ActorRef, x: Int, y: Int, color: String)
    case class SpriteLocation(sprite: ActorRef, x: Int, y: Int, color: String)
}