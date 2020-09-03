package ru.otus.sc.auth.router

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import de.heikoseeberger.akkahttpplayjson.PlayJsonSupport._
import ru.otus.sc.auth.json.AuthJsonProtocol._
import ru.otus.sc.auth.model.{LogInRequest, LogInResponse, SignUpRequest}
import ru.otus.sc.auth.service.AuthService
import ru.otus.sc.router.BaseRouter
import ru.otus.sc.user.model.{UserRequest, UserResponse}
import ru.otus.sc.user.service.UserService

class AuthRouter(authService: AuthService, userService: UserService) extends BaseRouter {
  override def route: Route = signUp ~ logIn

  private val signUp = AuthRouter.SignUpRouter(authService, userService).route
  private val logIn  = AuthRouter.LogInRouter(authService).route

}

object AuthRouter {
  case class SignUpRouter(authService: AuthService, userService: UserService) extends BaseRouter {
    override def route: Route =
      (post & pathPrefix("auth" / "signup") & entity(as[SignUpRequest])) { request =>
        val userResponse = userService.getUser(UserRequest(request.userId))
        userResponse match {
          case UserResponse.Found(_) =>
            complete(authService.signUp(request))
          case UserResponse.NotFound(_) => complete(StatusCodes.NotFound)
        }
      }
  }

  case class LogInRouter(service: AuthService) extends BaseRouter {
    override def route: Route =
      (post & pathPrefix("auth" / "login") & entity(as[LogInRequest])) { request =>
        val response = service.logIn(request)
        response match {
          case LogInResponse.Success(_) => complete(response)
          case LogInResponse.Fail       => complete(StatusCodes.Forbidden)
        }
      }
  }
}
