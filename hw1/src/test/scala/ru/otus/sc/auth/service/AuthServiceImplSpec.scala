package ru.otus.sc.auth.service

import java.util.UUID

import org.scalamock.scalatest.MockFactory
import org.scalatest.freespec.AnyFreeSpec
import ru.otus.sc.auth.dao.AuthDao
import ru.otus.sc.auth.model.{LogInRequest, LoginResponse, SignUpRequest, SignUpResponse}
import ru.otus.sc.user.model.User
import org.scalatest.matchers.should.Matchers._

class AuthServiceImplSpec extends AnyFreeSpec with MockFactory {
  private val user1 = User(Some(UUID.randomUUID()), "User1", "Test", 30)
  private val user2 = User(Some(UUID.randomUUID()), "User2", "Test", 24)

  "AuthServiceImpl tests" - {
    "signUp" - {
      "success sign up" in {
        val dao      = mock[AuthDao]
        val srv      = new AuthServiceImpl(dao)
        val login    = "user1"
        val password = "1234"

        (dao.exists _).expects(user1, login).returns(true)
        (dao.credentials _).expects(user1, login).returns(None)
        (dao.saveCredentials _).expects(user1, login, password)

        srv.signUp(SignUpRequest(user1, login, password)) shouldBe SignUpResponse(true)
      }

      "failed sign up" in {
        val dao      = mock[AuthDao]
        val srv      = new AuthServiceImpl(dao)
        val login    = "user1"
        val password = "1234"

        (dao.credentials _).expects(user1, login).returns(Some((login, password)))

        srv.signUp(SignUpRequest(user1, login, password)) shouldBe SignUpResponse(false)
      }
    }

    "logIn" - {
      "success log in" in {
        val dao      = mock[AuthDao]
        val srv      = new AuthServiceImpl(dao)
        val login    = "user1"
        val password = "1234"

        (dao.credentials _).expects(user1, login).returns(Some(login, password))

        srv.logIn(LogInRequest(user1, login, password)) shouldBe LoginResponse(true)
      }

      "failed log in" in {
        val dao      = mock[AuthDao]
        val srv      = new AuthServiceImpl(dao)
        val login    = "user1"
        val password = "1234"

        (dao.credentials _).expects(user1, login).returns(None)

        srv.logIn(LogInRequest(user1, login, password)) shouldBe LoginResponse(false)
      }
    }
  }
}
