package ru.otus.sc.session.dao

/**
 * DAO for session service
 */
trait SessionDao {
  /**
   * Get current session ID
   * @return
   */
  def getId: String
}

/**
 * Session DAO implementation that returns one time generated session ID
 */
class SessionDaoImpl extends SessionDao {
  lazy val getId: String = {
    val r = scala.util.Random
    val id = r.nextInt(1000)
    s"sessID$id"
  }
}
