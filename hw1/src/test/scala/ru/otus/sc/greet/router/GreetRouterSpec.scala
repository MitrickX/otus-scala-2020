package ru.otus.sc.greet.router

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalamock.scalatest.MockFactory
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers._
import ru.otus.sc.greet.dao.GreetingDaoImpl
import ru.otus.sc.greet.service.GreetingServiceImpl

class GreetRouterSpec extends AnyFreeSpec with ScalatestRouteTest with MockFactory {
  "Methods tests" - {
    "greet human" in {
      val dao    = new GreetingDaoImpl()
      val srv    = new GreetingServiceImpl(dao)
      val router = new GreetRouter(srv)

      Get("/greet/userName") ~> router.route ~> check {
        handled shouldBe true
        responseAs[String] shouldBe "Hi userName !"
        status shouldBe StatusCodes.OK
      }

      Get("/greet/userName?isHuman=true") ~> router.route ~> check {
        handled shouldBe true
        responseAs[String] shouldBe "Hi userName !"
        status shouldBe StatusCodes.OK
      }
    }

    "greet not human" in {
      val dao    = new GreetingDaoImpl()
      val srv    = new GreetingServiceImpl(dao)
      val router = new GreetRouter(srv)

      Get(s"/greet/userName?isHuman=false") ~> router.route ~> check {
        handled shouldBe true
        responseAs[String] shouldBe "AAAAAAAAAA!!!!!!"
        status shouldBe StatusCodes.OK
      }
    }
  }
}
