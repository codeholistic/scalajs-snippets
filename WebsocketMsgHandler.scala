import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, OverflowStrategy}
import akka.stream.scaladsl.{Flow, Keep, Sink, Source}
import akka.{Done, NotUsed}
import akka.http.scaladsl.model.ws.{Message, TextMessage}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

class WebsocketMsgHandler(implicit val system: ActorSystem,
                     implicit val materializer: ActorMaterializer,
                     implicit val executionContext: ExecutionContext) {

  def handleWebsocketMessages(route: Route): Unit = {
    val (upgradeResponse, closed) =
      upgradeResponseToWebsocket().flatMap { flow =>
        route {
          get {
            handleWebSocketMessages(flow)
          }
        }
      }.run()

    closed.onComplete {
      case Success(_) => println("Websocket closed")
      case Failure(e) => println(s"TODO handle error: ${e.getMessage}")
    }
  }

  def flow(onMessage: String => Unit): Flow[Message, Message, NotUsed] = {
    val incomingMessages: Sink[Message, NotUsed] =
      Flow[Message].collect {
        case TextMessage.Strict(txt) => txt
      }.to(Sink.foreach(onMessage))

    val outgoingMessages: Source[Message, NotUsed] =
      Source.actorRef[String](bufferSize = 10, overflowStrategy = OverflowStrategy.dropHead)
        .mapMaterializedValue { outActor =>
          system.actorOf(WebsocketActor.props(outActor))
        }.map(str => TextMessage(str))

    Flow.fromSinkAndSource(incomingMessages, outgoingMessages)
  }

  def handleWebsocketFlow(route: Route): Unit = {
    val (upgradeResponse, closed) =
      route.run()

    closed.onComplete {
      case Success(_) => println("Websocket closed")
      case Failure(e) => println(s"TODO error handling: ${e.getMessage}")
    }
  }
}
