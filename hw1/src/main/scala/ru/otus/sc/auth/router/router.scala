package ru.otus.sc.auth.router

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import de.heikoseeberger.akkahttpplayjson.PlayJsonSupport._
import ru.otus.sc.auth.json.AuthJsonProtocol._
import ru.otus.sc.auth.model.SignUpRequest
import ru.otus.sc.auth.service.AuthService
import ru.otus.sc.router.BaseRouter
import ru.otus.sc.user.model.{UserRequest, UserResponse}
import ru.otus.sc.user.service.UserService

class AuthRouter(authService: AuthService, userService: UserService) extends BaseRouter {
  override def route: Route =
    pathPrefix("auth") {
      signUp
    }

  private def signUp: Route =
    (post & pathPrefix("signup") & entity(as[SignUpRequest])) { request =>
      val userResponse = userService.getUser(UserRequest(request.userId))
      userResponse match {
        case UserResponse.Found(_) =>
          complete(authService.signUp(request))
        case UserResponse.NotFound(_) => complete(StatusCodes.NotFound)
      }
    }
}
