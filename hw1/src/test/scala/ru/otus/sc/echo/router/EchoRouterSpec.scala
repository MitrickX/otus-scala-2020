package ru.otus.sc.echo.router

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalamock.scalatest.MockFactory
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers._
import ru.otus.sc.echo.service.EchoServiceImpl

class EchoRouterSpec extends AnyFreeSpec with ScalatestRouteTest with MockFactory {
  "Methods tests" - {
    "echo" in {
      val srv    = new EchoServiceImpl
      val router = new EchoRouter(srv)

      Get("/echo/abc") ~> router.route ~> check {
        handled shouldBe true
        responseAs[String] shouldBe "abc"
        status shouldBe StatusCodes.OK
      }
    }
  }
}
