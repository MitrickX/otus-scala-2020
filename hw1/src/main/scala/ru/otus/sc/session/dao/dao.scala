package ru.otus.sc.session.dao

import java.util.UUID

import ru.otus.sc.user.model.User

/**
  * DAO for session service
  */
trait SessionDao {

  /**
    * Get current session ID
    * @return
    */
  def id: UUID

  /**
    * Save user in current session
    * @param id id of session
    * @param user current user
    */
  def user(id: UUID, user: User): Boolean

  /**
    * Get user of current session
    * @param id id of session
    * @return
    */
  def user(id: UUID): Option[User]
}

/**
  * Session DAO implementation
  */
class SessionDaoImpl extends SessionDao {
  private var users: Map[UUID, User] = Map.empty

  lazy val id = UUID.randomUUID()

  def user(id: UUID, user: User): Boolean = {
    users.get(id) match {
      case None =>
        users += (id -> user)
        true
      case Some(_) => false
    }
  }

  def user(id: UUID): Option[User] = users.get(id)
}
