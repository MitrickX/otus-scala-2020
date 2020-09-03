package ru.otus.sc.auth.dao

import java.util.UUID

import org.scalamock.scalatest.MockFactory
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers._

class AuthDaoImplSpec extends AnyFreeSpec with MockFactory {
  private val userId = UUID.randomUUID()

  "AuthDaoImpl tests" - {
    "saveCredentials" - {
      "should create credentials" in {
        val dao = new AuthDaoImpl
        dao.saveCredentials(userId, "user1", "1234")
        dao.exists(userId, "user1") shouldBe true
      }
    }

    "credentials" - {
      "should returns credentials" in {
        val dao = new AuthDaoImpl
        dao.saveCredentials(userId, "user1", "1234")
        dao.credentials(userId, "user1") shouldBe Some(("user1", "1234"))
      }

      "should returns none" in {
        val dao = new AuthDaoImpl
        dao.credentials(userId, "user1") shouldBe None
      }
    }

    "exists" - {
      "should exists" in {
        val dao = new AuthDaoImpl
        dao.saveCredentials(userId, "user1", "1234")
        dao.exists(userId, "user1") shouldBe true
      }
      "should not exists" in {
        val dao = new AuthDaoImpl
        dao.exists(userId, "user1") shouldBe false
      }
    }

    "find by login" - {

      "found" in {
        val dao   = new AuthDaoImpl
        val login = "user1"
        dao.saveCredentials(userId, "user1", "1234")
        dao.findByLogin(login) shouldBe Some(userId)
      }

      "not found" in {
        val dao    = new AuthDaoImpl
        val login1 = "user1"
        val login2 = "user2"
        dao.saveCredentials(userId, "user1", "1234")
        dao.findByLogin(login2) shouldBe None
      }
    }
  }
}
