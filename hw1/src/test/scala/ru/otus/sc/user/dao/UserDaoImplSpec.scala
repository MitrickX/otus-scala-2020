package ru.otus.sc.user.dao

import java.util.UUID

import org.scalamock.scalatest.MockFactory
import org.scalatest.freespec.AnyFreeSpec
import ru.otus.sc.user.model.User
import org.scalatest.matchers.should.Matchers._

class UserDaoImplSpec extends AnyFreeSpec with MockFactory {
  private val user1 = User(Some(UUID.randomUUID()), "User1", "Test", 30)
  private val user2 = User(Some(UUID.randomUUID()), "User2", "Test", 24)

  "UserDaoImpl tests" - {
    "createUser" - {
      "should create user" in {
        val dao          = new UserDaoImpl
        val user         = user1.copy(id = None)
        val createdUser  = dao.createUser(user)
        val expectedUser = user.copy(id = createdUser.id)

        createdUser shouldBe expectedUser
      }
    }

    "getUser" - {
      "should return user" in {
        val dao        = new UserDaoImpl
        val createUser = dao.createUser(user1.copy(id = None))
        val id         = createUser.id

        val user = dao.getUser(id.get)

        user.get shouldBe createUser
      }

      "should return none" in {
        val dao  = new UserDaoImpl
        val id   = UUID.randomUUID()
        val user = dao.getUser(id)

        user shouldBe None
      }
    }

    "updateUser" - {
      "should update user" in {
        val dao        = new UserDaoImpl
        val createUser = dao.createUser(user1.copy(id = None))
        val id         = createUser.id
        val user       = User(id, "Update", "Test", 19)

        val result = dao.updateUser(user)

        result.get shouldBe user
      }

      "should return none" in {
        val dao  = new UserDaoImpl
        val id   = UUID.randomUUID()
        val user = User(Some(id), "Update", "Test", 19)

        val result = dao.updateUser(user)

        result shouldBe None
      }

    }

    "deleteUser" - {
      "should delete user" in {
        val dao        = new UserDaoImpl
        val createUser = dao.createUser(user1.copy(id = None))
        val id         = createUser.id

        val result = dao.deleteUser(id.get)

        result.get shouldBe createUser
      }

      "should return none" in {
        val dao = new UserDaoImpl
        val id  = UUID.randomUUID()

        val result = dao.deleteUser(id)

        result shouldBe None
      }
    }

    "findByFirstName" - {
      "should return empty list" in {
        val dao       = new UserDaoImpl
        val firstName = "test"

        dao.findByFirstName(firstName) shouldBe Seq.empty
      }

      "should return non-empty list" in {
        val dao          = new UserDaoImpl
        val createdUser1 = dao.createUser(user1.copy(id = None, firstName = "test"))
        val createdUser2 = dao.createUser(user2.copy(id = None, firstName = "test"))

        dao.findByFirstName(createdUser1.firstName) shouldBe Seq(createdUser1, createdUser2)
      }
    }

    "findByLastName" - {
      "should return empty list" in {
        val dao      = new UserDaoImpl
        val lastName = "test"

        dao.findByLastName(lastName) shouldBe Seq.empty
      }

      "should return non-empty list" in {
        val dao          = new UserDaoImpl
        val createdUser1 = dao.createUser(user1.copy(id = None, lastName = "test"))
        val createdUser2 = dao.createUser(user2.copy(id = None, lastName = "test"))

        dao.findByLastName(createdUser1.lastName) shouldBe Seq(createdUser1, createdUser2)
      }
    }

    "findByAge" - {
      "should return empty list" in {
        val dao = new UserDaoImpl
        dao.findByAge(10, 50) shouldBe Seq.empty
      }

      "should return non-empty list" in {
        val dao          = new UserDaoImpl
        val createdUser1 = dao.createUser(user1.copy(id = None, age = 10))
        val createdUser2 = dao.createUser(user2.copy(id = None, age = 50))

        dao.findByAge(1, 25) shouldBe Seq(createdUser1)
        dao.findByAge(30, 80) shouldBe Seq(createdUser2)
        dao.findByAge(10, 50) shouldBe Seq(createdUser1, createdUser2)
      }
    }

    "find" - {
      "should return empty list" in {
        val dao = new UserDaoImpl
        dao.createUser(user1.copy(id = None))
        dao.createUser(user2.copy(id = None))

        dao.find(from = Some(10), to = Some(15)) shouldBe Seq.empty
        dao.find(firstName = Some("abc"), lastName = Some("xyzz")) shouldBe Seq.empty
        dao.find(firstName = Some("xxx"), lastName = Some("Test")) shouldBe Seq.empty
      }

      "should return non-empty list" in {
        val dao = new UserDaoImpl
        val u1  = dao.createUser(user1.copy(id = None))
        val u2  = dao.createUser(user2.copy(id = None))

        dao.find(from = Some(10), to = Some(24)) shouldBe Seq(u2)
        dao.find(firstName = Some(u1.firstName), lastName = Some(u1.lastName)) shouldBe Seq(u1)
        dao.find(lastName = Some("Test")) shouldBe Seq(u1, u2)
        dao.find(from = Some(10)) shouldBe Seq(u1, u2)
      }
    }

    "allUsers" - {
      "should return empty list" in {
        val dao = new UserDaoImpl
        dao.allUsers shouldBe Seq.empty
      }

      "should return non-empty list" in {
        val dao          = new UserDaoImpl
        val createdUser1 = dao.createUser(user1.copy(id = None, age = 10))
        val createdUser2 = dao.createUser(user2.copy(id = None, age = 50))

        dao.allUsers shouldBe Seq(createdUser1, createdUser2)
      }
    }

  }
}
