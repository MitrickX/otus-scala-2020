package ru.otus.sc.user.service

import java.util.UUID

import org.scalamock.scalatest.MockFactory
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers._
import ru.otus.sc.user.dao.UserDao
import ru.otus.sc.user.model.{
  CreateUserRequest,
  CreateUserResponse,
  DeleteUserRequest,
  DeleteUserResponse,
  FindUsersRequest,
  FindUsersResponse,
  UserRequest,
  UserResponse,
  UpdateUserRequest,
  UpdateUserResponse,
  User
}

class UserServiceImplSpec extends AnyFreeSpec with MockFactory {
  private val user1 = User(Some(UUID.randomUUID()), "User1", "Test", 30)
  private val user2 = User(Some(UUID.randomUUID()), "User2", "Test", 24)

  "UserServiceImpl tests" - {

    "createUser" - {
      "should create user" in {
        val dao = mock[UserDao]
        val srv = new UserServiceImpl(dao)

        (dao.createUser _).expects(user1).returns(user2)

        srv.createUser(CreateUserRequest(user1)) shouldBe CreateUserResponse(user2)
      }
    }

    "getUser" - {
      "should return user" in {
        val dao = mock[UserDao]
        val srv = new UserServiceImpl(dao)
        val id  = UUID.randomUUID()

        (dao.getUser _).expects(id).returns(Some(user1))

        srv.getUser(UserRequest(id)) shouldBe UserResponse.Found(user1)
      }

      "should return not found" in {
        val dao = mock[UserDao]
        val srv = new UserServiceImpl(dao)
        val id  = UUID.randomUUID()

        (dao.getUser _).expects(id).returns(None)

        srv.getUser(UserRequest(id)) shouldBe UserResponse.NotFound(id)
      }
    }

    "updateUser" - {
      "should update user" in {
        val dao = mock[UserDao]
        val srv = new UserServiceImpl(dao)

        (dao.updateUser _).expects(user1).returns(Some(user2))

        srv.updateUser(UpdateUserRequest(user1)) shouldBe UpdateUserResponse.Updated(user2)
      }

      "should return not found" in {
        val dao = mock[UserDao]
        val srv = new UserServiceImpl(dao)

        (dao.updateUser _).expects(user1).returns(None)

        srv.updateUser(UpdateUserRequest(user1)) shouldBe UpdateUserResponse.NotFound(user1.id.get)
      }

      "should return require user id response" in {
        val dao  = mock[UserDao]
        val srv  = new UserServiceImpl(dao)
        val user = user1.copy(id = None)

        srv.updateUser(UpdateUserRequest(user)) shouldBe UpdateUserResponse.RequireUserID
      }
    }

    "deleteUser" - {
      "should delete user" in {
        val dao = mock[UserDao]
        val srv = new UserServiceImpl(dao)
        val id  = UUID.randomUUID()

        (dao.deleteUser _).expects(id).returns(Some(user1))

        srv.deleteUser(DeleteUserRequest(id)) shouldBe DeleteUserResponse.Deleted(user1)
      }

      "should return not found" in {
        val dao = mock[UserDao]
        val srv = new UserServiceImpl(dao)
        val id  = UUID.randomUUID()

        (dao.deleteUser _).expects(id).returns(None)

        srv.deleteUser(DeleteUserRequest(id)) shouldBe DeleteUserResponse.NotFound(id)
      }
    }

    "findUsers" - {
      "by first name" - {
        "should return empty list" in {
          val dao       = mock[UserDao]
          val srv       = new UserServiceImpl(dao)
          val firstName = "test"

          (dao.findByFirstName _).expects(firstName).returns(Seq.empty)

          srv.findUsers(FindUsersRequest.ByFirstName(firstName)) shouldBe FindUsersResponse(
            Seq.empty
          )
        }

        "should return non-empty list" in {
          val dao       = mock[UserDao]
          val srv       = new UserServiceImpl(dao)
          val firstName = "test"

          (dao.findByFirstName _).expects(firstName).returns(Seq(user1, user2))

          srv.findUsers(FindUsersRequest.ByFirstName(firstName)) shouldBe FindUsersResponse(
            Seq(user1, user2)
          )
        }
      }

      "by last name" - {
        "should return empty list" in {
          val dao      = mock[UserDao]
          val srv      = new UserServiceImpl(dao)
          val lastName = "test"

          (dao.findByLastName _).expects(lastName).returns(Seq.empty)

          srv.findUsers(FindUsersRequest.ByLastName(lastName)) shouldBe FindUsersResponse(
            Seq.empty
          )
        }

        "should return non-empty list" in {
          val dao      = mock[UserDao]
          val srv      = new UserServiceImpl(dao)
          val lastName = "test"

          (dao.findByLastName _).expects(lastName).returns(Seq(user1, user2))

          srv.findUsers(FindUsersRequest.ByLastName(lastName)) shouldBe FindUsersResponse(
            Seq(user1, user2)
          )
        }
      }

      "by name" - {
        "should return empty list" in {
          val dao       = mock[UserDao]
          val srv       = new UserServiceImpl(dao)
          val firstName = "abc"
          val lastName  = "test"

          (dao.findByName _).expects(firstName, lastName).returns(Seq.empty)

          srv.findUsers(FindUsersRequest.ByName(firstName, lastName)) shouldBe FindUsersResponse(
            Seq.empty
          )
        }

        "should return non-empty list" in {
          val dao       = mock[UserDao]
          val srv       = new UserServiceImpl(dao)
          val firstName = "abc"
          val lastName  = "test"

          (dao.findByName _).expects(firstName, lastName).returns(Seq(user1, user2))

          srv.findUsers(FindUsersRequest.ByName(firstName, lastName)) shouldBe FindUsersResponse(
            Seq(user1, user2)
          )
        }
      }

      "by age" - {
        "should return empty list" in {
          val dao  = mock[UserDao]
          val srv  = new UserServiceImpl(dao)
          val from = 20
          val to   = 30

          (dao.findByAge _).expects(from, to).returns(Seq.empty)

          srv.findUsers(FindUsersRequest.ByAge(from, to)) shouldBe FindUsersResponse(
            Seq.empty
          )
        }

        "should return non-empty list" in {
          val dao  = mock[UserDao]
          val srv  = new UserServiceImpl(dao)
          val from = 20
          val to   = 30

          (dao.findByAge _).expects(from, to).returns(Seq(user1, user2))

          srv.findUsers(FindUsersRequest.ByAge(from, to)) shouldBe FindUsersResponse(
            Seq(user1, user2)
          )
        }
      }

      "all" - {
        "should return empty list" in {
          val dao = mock[UserDao]
          val srv = new UserServiceImpl(dao)

          (dao.allUsers _).expects().returns(Seq.empty)

          srv.findUsers(FindUsersRequest.All) shouldBe FindUsersResponse(
            Seq.empty
          )
        }

        "should return non-empty list" in {
          val dao = mock[UserDao]
          val srv = new UserServiceImpl(dao)
          (dao.allUsers _).expects().returns(Seq(user1, user2))

          srv.findUsers(FindUsersRequest.All) shouldBe FindUsersResponse(
            Seq(user1, user2)
          )
        }
      }
    }
  }
}
