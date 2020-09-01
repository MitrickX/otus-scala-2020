package ru.otus.sc.user.router

import java.util.UUID

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalamock.scalatest.MockFactory
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers._
import ru.otus.sc.user.model.{
  CreateUserRequest,
  CreateUserResponse,
  DeleteUserRequest,
  DeleteUserResponse,
  UpdateUserRequest,
  UpdateUserResponse,
  User,
  UserRequest,
  UserResponse
}
import ru.otus.sc.user.service.UserServiceImpl
import play.api.libs.json._
import ru.otus.sc.user.json.UserJsonProtocol._
import de.heikoseeberger.akkahttpplayjson.PlayJsonSupport._

class UserRouterSpec extends AnyFreeSpec with ScalatestRouteTest with MockFactory {
  "Methods tests" - {
    "get one" - {
      "found" in {
        val srv    = mock[UserServiceImpl]
        val router = new UserRouter(srv)
        val userId = UUID.randomUUID()
        val user   = User(id = Some(userId), firstName = "test", lastName = "get", age = 33)

        (srv.getUser _).expects(UserRequest(userId)).returns(UserResponse.Found(user))

        Get(s"/users/$userId") ~> router.route ~> check {
          handled shouldBe true
          responseAs[User] shouldBe user
          status shouldBe StatusCodes.OK
        }
      }
      "not found" in {
        val srv    = mock[UserServiceImpl]
        val router = new UserRouter(srv)
        val userId = UUID.randomUUID()

        (srv.getUser _).expects(UserRequest(userId)).returns(UserResponse.NotFound(userId))

        Get(s"/users/$userId") ~> router.route ~> check {
          handled shouldBe true
          status shouldBe StatusCodes.NotFound
        }
      }
    }

    "create" in {
      val srv         = mock[UserServiceImpl]
      val router      = new UserRouter(srv)
      val user        = User(id = None, firstName = "test", lastName = "create", age = 25)
      val userId      = UUID.randomUUID()
      val createdUser = user.copy(id = Some(userId))

      (srv.createUser _).expects(CreateUserRequest(user)).returns(CreateUserResponse(createdUser))

      Post("/users", Json.toJson(user)) ~> router.route ~> check {
        handled shouldBe true
        responseAs[User] shouldBe createdUser
        status shouldBe StatusCodes.OK
      }
    }

    "update" - {
      "updated" in {
        val srv         = mock[UserServiceImpl]
        val router      = new UserRouter(srv)
        val userId      = UUID.randomUUID()
        val user        = User(id = Some(userId), firstName = "test", lastName = "update", age = 25)
        val updatedUser = user.copy(age = 33)

        (srv.updateUser _)
          .expects(UpdateUserRequest(updatedUser))
          .returns(UpdateUserResponse.Updated(updatedUser))

        Put(s"/users/$userId", Json.toJson(updatedUser)) ~> router.route ~> check {
          handled shouldBe true
          responseAs[UpdateUserResponse] shouldBe UpdateUserResponse.Updated(updatedUser)
          status shouldBe StatusCodes.OK
        }
      }

      "not found" in {
        val srv         = mock[UserServiceImpl]
        val router      = new UserRouter(srv)
        val userId      = UUID.randomUUID()
        val updatedUser = User(id = Some(userId), firstName = "test", lastName = "update", age = 25)

        (srv.updateUser _)
          .expects(UpdateUserRequest(updatedUser))
          .returns(UpdateUserResponse.NotFound(userId))

        Put(s"/users/$userId", Json.toJson(updatedUser)) ~> router.route ~> check {
          handled shouldBe true
          responseAs[UpdateUserResponse] shouldBe UpdateUserResponse.NotFound(userId)
          status shouldBe StatusCodes.OK
        }
      }

      "require id" in {
        val srv         = mock[UserServiceImpl]
        val router      = new UserRouter(srv)
        val userId      = UUID.randomUUID()
        val updatedUser = User(id = Some(userId), firstName = "test", lastName = "update", age = 25)

        Put("/users", Json.toJson(updatedUser)) ~> router.route ~> check {
          handled shouldBe false
        }
      }
    }

    "delete" - {
      "deleted" in {
        val srv    = mock[UserServiceImpl]
        val router = new UserRouter(srv)
        val userId = UUID.randomUUID()
        val user   = User(id = Some(userId), firstName = "test", lastName = "update", age = 25)

        (srv.deleteUser _)
          .expects(DeleteUserRequest(userId))
          .returns(DeleteUserResponse.Deleted(user))

        Delete(s"/users/$userId") ~> router.route ~> check {
          handled shouldBe true
          responseAs[DeleteUserResponse] shouldBe DeleteUserResponse.Deleted(user)
          status shouldBe StatusCodes.OK
        }
      }

      "not found" in {
        val srv    = mock[UserServiceImpl]
        val router = new UserRouter(srv)
        val userId = UUID.randomUUID()

        (srv.deleteUser _)
          .expects(DeleteUserRequest(userId))
          .returns(DeleteUserResponse.NotFound(userId))

        Delete(s"/users/$userId") ~> router.route ~> check {
          handled shouldBe true
          responseAs[DeleteUserResponse] shouldBe DeleteUserResponse.NotFound(userId)
          status shouldBe StatusCodes.OK
        }
      }

      "require id" in {
        val srv    = mock[UserServiceImpl]
        val router = new UserRouter(srv)

        Delete("/users") ~> router.route ~> check {
          handled shouldBe false
        }
      }
    }
  }
}
