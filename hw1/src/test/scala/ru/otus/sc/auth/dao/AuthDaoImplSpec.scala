package ru.otus.sc.auth.dao

import java.util.UUID

import org.scalamock.scalatest.MockFactory
import org.scalatest.freespec.AnyFreeSpec
import ru.otus.sc.user.model.User
import org.scalatest.matchers.should.Matchers._

class AuthDaoImplSpec extends AnyFreeSpec with MockFactory {
  private val user = User(Some(UUID.randomUUID()), "User1", "Test", 30)

  "AuthDaoImpl tests" - {
    "saveCredentials" - {
      "should create credentials" in {
        val dao = new AuthDaoImpl
        dao.saveCredentials(user, "user1", "1234")
        dao.exists(user, "user1") shouldBe true
      }
    }

    "credentials" - {
      "should returns credentials" in {
        val dao = new AuthDaoImpl
        dao.saveCredentials(user, "user1", "1234")
        dao.credentials(user, "user1") shouldBe Some(("user1", "1234"))
      }

      "should returns none" in {
        val dao = new AuthDaoImpl
        dao.credentials(user, "user1") shouldBe None
      }
    }

    "exists" - {
      "should exists" in {
        val dao = new AuthDaoImpl
        dao.saveCredentials(user, "user1", "1234")
        dao.exists(user, "user1") shouldBe true
      }
      "should not exists" in {
        val dao = new AuthDaoImpl
        dao.exists(user, "user1") shouldBe false
      }
    }
  }
}
