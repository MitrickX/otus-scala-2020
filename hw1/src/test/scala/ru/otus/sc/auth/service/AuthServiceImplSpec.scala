package ru.otus.sc.auth.service

import java.util.UUID

import org.scalamock.scalatest.MockFactory
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers._
import ru.otus.sc.auth.dao.AuthDao
import ru.otus.sc.auth.model.{LogInRequest, LoginResponse, SignUpRequest, SignUpResponse}

class AuthServiceImplSpec extends AnyFreeSpec with MockFactory {
  private val userId = UUID.randomUUID()

  "AuthServiceImpl tests" - {
    "signUp" - {
      "already signed up" in {
        val dao   = mock[AuthDao]
        val srv   = new AuthServiceImpl(dao)
        val login = "user1"

        (dao.exists _).expects(userId, login).returns(true)

        srv.signUp(SignUpRequest(userId, login, "1234")) shouldBe SignUpResponse.AlreadySignedUp
      }

      "success sign up" in {
        val dao      = mock[AuthDao]
        val srv      = new AuthServiceImpl(dao)
        val login    = "user1"
        val password = "1234"

        (dao.exists _).expects(userId, login).returns(false)
        (dao.exists _).expects(userId, login).returns(true)
        (dao.saveCredentials _).expects(userId, login, password)

        srv.signUp(SignUpRequest(userId, login, password)) shouldBe SignUpResponse.Success
      }

      "failed sign up" in {
        val dao      = mock[AuthDao]
        val srv      = new AuthServiceImpl(dao)
        val login    = "user1"
        val password = "1234"

        (dao.exists _).expects(userId, login).returns(false).atLeastTwice()
        (dao.saveCredentials _).expects(userId, login, password)

        srv.signUp(SignUpRequest(userId, login, password)) shouldBe SignUpResponse.Fail
      }
    }

    "logIn" - {
      "success log in" in {
        val dao      = mock[AuthDao]
        val srv      = new AuthServiceImpl(dao)
        val login    = "user1"
        val password = "1234"

        (dao.credentials _).expects(userId, login).returns(Some(login, password))

        srv.logIn(LogInRequest(userId, login, password)) shouldBe LoginResponse(true)
      }

      "failed log in" in {
        val dao      = mock[AuthDao]
        val srv      = new AuthServiceImpl(dao)
        val login    = "user1"
        val password = "1234"

        (dao.credentials _).expects(userId, login).returns(None)

        srv.logIn(LogInRequest(userId, login, password)) shouldBe LoginResponse(false)
      }
    }
  }
}
