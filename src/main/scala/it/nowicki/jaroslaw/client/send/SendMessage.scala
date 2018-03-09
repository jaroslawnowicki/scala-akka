package it.nowicki.jaroslaw.client.send

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshalling.Marshal
import akka.http.scaladsl.model._
import akka.stream.{ActorMaterializer, OverflowStrategy, QueueOfferResult}
import akka.stream.scaladsl.{Keep, Sink, Source}

import scala.concurrent.{Await, Future, Promise}
import scala.concurrent.duration._
import scala.util.{Failure, Success}

import scala.concurrent.ExecutionContext.Implicits.global

object SendMessage {

  def send() : Unit = {
    val sendMessage = new SendMessage
    sendMessage.send()
  }
}

class SendMessage {

  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()

  val poolClientFlow = Http().cachedHostConnectionPool[Promise[HttpResponse]]("localhost", 8080)

  val QueueSize = 10

  val queue =
    Source.queue[(HttpRequest, Promise[HttpResponse])](QueueSize, OverflowStrategy.dropNew)
      .via(poolClientFlow)
      .toMat(Sink.foreach({
        case ((Success(resp), p)) => p.success(resp)
        case ((Failure(e), p))    => p.failure(e)
      }))(Keep.left)
      .run()

  def queueRequest(request: HttpRequest): Future[HttpResponse] = {
    val responsePromise = Promise[HttpResponse]()
    queue.offer(request -> responsePromise).flatMap {
      case QueueOfferResult.Enqueued    => responsePromise.future
      case QueueOfferResult.Dropped     => Future.failed(new RuntimeException("Queue overflowed. Try again later."))
      case QueueOfferResult.Failure(ex) => Future.failed(ex)
      case QueueOfferResult.QueueClosed => Future.failed(new RuntimeException("Queue was closed (pool shut down) while running the request. Try again later."))
    }
  }

  def createMessage() : RequestEntity= {
    val string = "Yeah"
    val entityFuture = Marshal(string).to[MessageEntity]
    Await.result(entityFuture, 1.second)
  }

  def createHttpRequest(): HttpRequest = {
    HttpRequest(
      HttpMethods.POST,
      uri = "/upload",
      entity = createMessage(),
      headers = Nil,
      protocol = HttpProtocols.`HTTP/1.1`)
  }



  def send() : Unit = {
    val responseFuture: Future[HttpResponse] = queueRequest(HttpRequest(uri = "/hello"))

    val responseFuture1: Future[HttpResponse] = queueRequest(HttpRequest(uri = "/hello"))

    val uploadFuture: Future[HttpResponse] = queueRequest(createHttpRequest())
  }
}
