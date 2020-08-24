package scala.ru.otus.sc.session.dao

import java.util.UUID

import org.scalamock.scalatest.MockFactory
import org.scalatest.freespec.AnyFreeSpec
import ru.otus.sc.session.dao.SessionDaoImpl
import ru.otus.sc.user.model.User
import org.scalatest.matchers.should.Matchers._

class SessionDaoImplSpec extends AnyFreeSpec with MockFactory {
  private val user = User(Some(UUID.randomUUID()), "test", "user", 30)

  "SessionDaoImpl tests" - {
    "id" - {
      "should returns id" in {
        val dao = new SessionDaoImpl

        assert(dao.id.isInstanceOf[UUID])
      }
    }

    "set user" - {
      "should set user" in {
        val dao = new SessionDaoImpl
        val id  = dao.id

        dao.user(id, user) shouldBe true
      }

      "should set user only once" in {
        val dao = new SessionDaoImpl
        val id  = dao.id

        dao.user(id, user)
        dao.user(id, user) shouldBe false
      }
    }

    "get user" - {
      "should get user" in {
        val dao = new SessionDaoImpl
        val id  = dao.id

        dao.user(id, user)
        dao.user(id) shouldBe Some(user)
      }

      "should get none" in {
        val dao = new SessionDaoImpl

        dao.user(UUID.randomUUID()) shouldBe None
      }
    }
  }
}
