package ru.otus.sc.greet.router

import akka.http.scaladsl.server.Route
import ru.otus.sc.router.BaseRouter
import akka.http.scaladsl.server.Directives._
import ru.otus.sc.greet.model.GreetRequest
import ru.otus.sc.greet.service.GreetingService

class GreetRouter(service: GreetingService) extends BaseRouter {
  override def route: Route =
    pathPrefix("greet") {
      greet
    }

  val isHumanParam = parameter("isHuman".as[Boolean] ? true)

  private def greet: Route =
    (get & path(Segment) & isHumanParam) { (name, isHuman) =>
      val response = service.greet(GreetRequest(name, isHuman))
      complete(response.greeting)
    }
}
