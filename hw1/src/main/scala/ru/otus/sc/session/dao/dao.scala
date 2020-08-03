package ru.otus.sc.session.dao

/**
 * DAO for session service
 */
trait SessionDao {
  /**
   * Get current session ID
   * @return
   */
  def id: String
}

/**
 * Session DAO implementation that returns one time generated session ID
 */
class SessionDaoImpl extends SessionDao {
  lazy val id: String = {
    val r = scala.util.Random
    val id = r.nextInt(1_000)
    s"sessID$id"
  }
}
