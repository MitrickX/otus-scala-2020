package ru.otus.sc.auth.router

import java.util.UUID

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.testkit.ScalatestRouteTest
import de.heikoseeberger.akkahttpplayjson.PlayJsonSupport._
import org.scalamock.scalatest.MockFactory
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers._
import play.api.libs.json.Json
import ru.otus.sc.auth.dao.AuthDaoImpl
import ru.otus.sc.auth.json.AuthJsonProtocol._
import ru.otus.sc.auth.model.{LogInRequest, LogInResponse, SignUpRequest, SignUpResponse}
import ru.otus.sc.auth.service.{AuthService, AuthServiceImpl}
import ru.otus.sc.user.model.{User, UserRequest, UserResponse}
import ru.otus.sc.user.service.UserService

class AuthRouterSpec extends AnyFreeSpec with ScalatestRouteTest with MockFactory {
  "Methods tests" - {
    "signup" - {
      "user not found" in {
        val userService = mock[UserService]
        val userId      = UUID.randomUUID()

        (userService.getUser _).expects(UserRequest(userId)).returns(UserResponse.NotFound(userId))

        val authDao     = new AuthDaoImpl
        val authService = new AuthServiceImpl(authDao)

        val router = AuthRouter.SignUpRouter(authService, userService)

        val request = SignUpRequest(userId, "user1", "1234")

        Post(s"/auth/signup/$userId", Json.toJson(request)) ~> router.route ~> check {
          handled shouldBe true
          status shouldBe StatusCodes.NotFound
        }
      }

      "successful" in {
        val userService = mock[UserService]
        val userId      = UUID.randomUUID()
        val user        = User(Some(userId), "user2", "test", 35)

        (userService.getUser _).expects(UserRequest(userId)).returns(UserResponse.Found(user))

        val authDao     = new AuthDaoImpl
        val authService = new AuthServiceImpl(authDao)

        val router = AuthRouter.SignUpRouter(authService, userService)

        val request = SignUpRequest(userId, "user1", "1234")

        Post(s"/auth/signup/$userId", Json.toJson(request)) ~> router.route ~> check {
          handled shouldBe true
          responseAs[SignUpResponse] shouldBe SignUpResponse.Success
          status shouldBe StatusCodes.OK
        }
      }

      "fail" in {
        val authService = mock[AuthService]
        val userService = mock[UserService]

        val router  = AuthRouter.SignUpRouter(authService, userService)
        val userId  = UUID.randomUUID()
        val request = SignUpRequest(userId, "user1", "1234")

        val user = User(Some(userId), "user2", "test", 35)

        (userService.getUser _).expects(UserRequest(userId)).returns(UserResponse.Found(user))

        (authService.signUp _).expects(request).returns(SignUpResponse.Fail)

        Post(s"/auth/signup/$userId", Json.toJson(request)) ~> router.route ~> check {
          handled shouldBe true
          responseAs[SignUpResponse] shouldBe SignUpResponse.Fail
          status shouldBe StatusCodes.OK
        }
      }
    }

    "login" - {
      "successful" in {
        val authService = mock[AuthService]
        val login       = "test"
        val password    = "1234"
        val userId      = UUID.randomUUID()

        (authService.logIn _)
          .expects(LogInRequest(login, password))
          .returns(LogInResponse.Success(userId))

        val router = AuthRouter.LogInRouter(authService)

        val request = LogInRequest(login, password)

        Post(s"/auth/login/", Json.toJson(request)) ~> router.route ~> check {
          handled shouldBe true
          responseAs[LogInResponse] shouldBe LogInResponse.Success(userId)
          status shouldBe StatusCodes.OK
        }
      }

      "fail" in {
        val authService = mock[AuthService]
        val login       = "test"
        val password    = "1234"

        (authService.logIn _)
          .expects(LogInRequest(login, password))
          .returns(LogInResponse.Fail)

        val router = AuthRouter.LogInRouter(authService)

        val request = LogInRequest(login, password)

        Post(s"/auth/login/", Json.toJson(request)) ~> router.route ~> check {
          handled shouldBe true
          status shouldBe StatusCodes.Forbidden
        }
      }

    }

  }
}
