package scala.ru.otus.sc.session.service

import java.util.UUID

import org.scalamock.scalatest.MockFactory
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers._
import ru.otus.sc.session.dao.SessionDao
import ru.otus.sc.session.model._
import ru.otus.sc.session.service.SessionServiceImpl
import ru.otus.sc.user.model.User

class SessionServiceImplSpec extends AnyFreeSpec with MockFactory {
  private val user = User(Some(UUID.randomUUID()), "test", "user", 30)

  "SessionServiceImpl tests" - {

    "id" - {
      "should returns id" in {
        val dao = mock[SessionDao]
        val srv = new SessionServiceImpl(dao)
        val id  = UUID.randomUUID()

        (dao.id _).expects().returns(id)

        srv.id shouldBe SessionIdResponse(id)

      }
    }

    "set user" - {
      "should set user" in {
        val dao = mock[SessionDao]
        val srv = new SessionServiceImpl(dao)
        val id  = UUID.randomUUID()

        (dao.user: (UUID, User) => Boolean).expects(id, user).returns(true)

        srv.user(SetSessionUserRequest(id, user)) shouldBe SetSessionUserResponse.Success
      }

      "should set user only once" in {
        val dao = mock[SessionDao]
        val srv = new SessionServiceImpl(dao)
        val id  = UUID.randomUUID()

        (dao.user: (UUID, User) => Boolean).expects(id, user).returns(false)

        srv.user(SetSessionUserRequest(id, user)) shouldBe SetSessionUserResponse.AlreadySet
      }
    }

    "get user" - {
      "should get user" in {
        val dao = mock[SessionDao]
        val srv = new SessionServiceImpl(dao)
        val id  = UUID.randomUUID()

        (dao.user: UUID => Option[User]).expects(id).returns(Some(user))

        srv.user(SessionUserRequest(id)) shouldBe SessionUserResponse.Found(user)
      }

      "should get not found" in {
        val dao = mock[SessionDao]
        val srv = new SessionServiceImpl(dao)
        val id  = UUID.randomUUID()

        (dao.user: UUID => Option[User]).expects(id).returns(None)

        srv.user(SessionUserRequest(id)) shouldBe SessionUserResponse.NotFound
      }
    }
  }
}
