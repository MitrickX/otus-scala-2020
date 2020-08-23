package ru.otus.sc.auth.dao
import ru.otus.sc.user.model.User

trait AuthDao {
  def saveCredentials(user: User, login: String, password: String): Unit
  def credentials(user: User, login: String): Option[(String, String)]
  def exists(user: User, login: String): Boolean
}

/**
  * Auth DAO implementation
  */
class AuthDaoImpl extends AuthDao {
  private var credentials: Map[User, (String, String)] = Map.empty

  def saveCredentials(user: User, login: String, password: String): Unit =
    credentials += user -> (login, password)

  def credentials(user: User, login: String): Option[(String, String)] =
    credentials.get(user)

  def exists(user: User, login: String): Boolean =
    credentials.get(user) match {
      case None         => false
      case Some((l, _)) => l == login
    }
}
