package ru.otus.sc.auth.dao
import java.util.UUID

trait AuthDao {
  def saveCredentials(userId: UUID, login: String, password: String): Unit
  def credentials(userId: UUID, login: String): Option[(String, String)]
  def findByLogin(login: String): Option[UUID]
  def exists(userId: UUID, login: String): Boolean
}

/**
  * Auth DAO implementation
  */
class AuthDaoImpl extends AuthDao {
  private var credentials: Map[UUID, (String, String)] = Map.empty

  def saveCredentials(userId: UUID, login: String, password: String): Unit =
    credentials += userId -> (login, password)

  def credentials(userId: UUID, login: String): Option[(String, String)] =
    credentials.get(userId)

  def findByLogin(login: String): Option[UUID] =
    credentials.find(_._2._1 == login).map(_._1)

  def exists(userId: UUID, login: String): Boolean =
    credentials.get(userId) match {
      case None         => false
      case Some((l, _)) => l == login
    }
}
