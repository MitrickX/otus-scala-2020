package ru.otus.sc.session.dao.impl

import ru.otus.sc.session.dao.SessionDao

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
