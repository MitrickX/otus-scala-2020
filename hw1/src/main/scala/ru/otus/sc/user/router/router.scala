import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives.{get, _}
import akka.http.scaladsl.server.Route
import de.heikoseeberger.akkahttpplayjson.PlayJsonSupport._
import ru.otus.sc.router.BaseRouter
import ru.otus.sc.user.json.UserJsonProtocol._
import ru.otus.sc.user.model.{CreateUserRequest, FindUsersRequest, User, UserRequest, UserResponse}
import ru.otus.sc.user.service.UserService

class UserRouter(service: UserService) extends BaseRouter {
  override def route: Route =
    pathPrefix("users") {
      getUser ~ getUsers ~ createUser
    }

  private def getUser: Route =
    (get & path(JavaUUID)) { id =>
      service.getUser(UserRequest(id)) match {
        case UserResponse.Found(user) => complete(user)
        case UserResponse.NotFound(_) => complete(StatusCodes.NotFound)
      }
    }

  private def createUser: Route =
    (post & entity(as[User])) { user =>
      val response = service.createUser(CreateUserRequest(user))
      complete(response.user)
    }

  val queryParams = parameters(
    "firstName".as[String].?,
    "lastName".as[String].?,
    "from".as[Int].?,
    "to".as[Int].?
  )

  private def getUsers: Route =
    (get & queryParams) { (firstName, lastName, from, to) =>
      val request  = FindUsersRequest.Find(firstName, lastName, from, to)
      val response = service.findUsers(request)
      complete(response.users)
    }
}
