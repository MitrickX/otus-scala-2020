package ru.otus.sc.router

import akka.http.scaladsl.server.Route

trait BaseRouter {
  def route: Route
}
