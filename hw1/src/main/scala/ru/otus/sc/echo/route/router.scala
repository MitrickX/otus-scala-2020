package ru.otus.sc.echo.router

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import ru.otus.sc.echo.model.EchoRequest
import ru.otus.sc.echo.service.EchoService
import ru.otus.sc.router.BaseRouter

class EchoRouter(service: EchoService) extends BaseRouter {
  override def route: Route =
    pathPrefix("echo") {
      echo
    }

  private def echo: Route =
    (get & path(Segment)) { msg =>
      val response = service.echo(EchoRequest(msg))
      complete(response.message)
    }
}
