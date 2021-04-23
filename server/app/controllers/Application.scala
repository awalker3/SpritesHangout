package controllers

import javax.inject._

import shared.SharedMessages
import play.api.mvc._
import actors.actors.SpriteManager
import actors.SpriteActor

import play.api.i18n._
import play.api.libs.json._
import akka.actor.Actor
import play.api.libs.streams.ActorFlow
import akka.actor.ActorSystem
import akka.stream.Materializer
import akka.actor.Props

@Singleton
class Application @Inject()(cc: ControllerComponents)(implicit system: ActorSystem, mat: Materializer) extends AbstractController(cc) {
  val manager = system.actorOf(Props[SpriteManager], "Manager")

  def index = Action { implicit request =>
    Ok(views.html.sprites())
  }

  def socket = WebSocket.accept[String, String] { request =>
    ActorFlow.actorRef { out =>
      SpriteActor.props(out, manager)
    }
  }

} 
