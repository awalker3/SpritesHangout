package actors

import akka.actor.Actor
import akka.actor.ActorRef
import akka.actor.Props
import _root_.actors.actors.SpriteManager
import play.api.libs.json.Json
import play.api.libs.json.JsSuccess
import play.api.libs.json.JsError

class SpriteActor(out: ActorRef, manager: ActorRef, var x: Int, var y: Int, var color: String) extends Actor {
    manager ! SpriteManager.NewSprite(self, x, y, color)
    import SpriteActor._
    def receive = {
        case (a,b,c,d) => println("wut")
        case DrawSprite(lst) => {
            // var str: String = ""
            // for (i <- lst) {
            //     val ss = "" + i._1 + "," + i._2 + "," + i._3 + " "
            //     str = str ++ ss
            // }
            out ! Json.toJson(lst).toString()
        }
            //println(s"Gotta draw $x $y $color")
        case s: String => stringParse(s)
        case m => println("Unhandled message in SpriteActor: " + m)
    }

    def stringParse (s: String): Unit = {
        println("beginning of stringParse: " + s)
        val (x,y,color) = Json.fromJson[(Int, Int, String)] (Json.parse(s))
        match {
            case JsSuccess (tup, path) => tup
            case JsError (error) => {
                println("error in parsing in SpriteActor" + error)
                (0, 0, "error")
            }
        }
        
        manager ! SpriteManager.SpriteLocation(self,x,y,color)
    }
}

object SpriteActor {
    def props(out: ActorRef, manager: ActorRef) = Props(new SpriteActor(out, manager, 160, 100, "#073b4c"))
    case class DrawSprite(lst: List[(Int, Int, String)])
}

