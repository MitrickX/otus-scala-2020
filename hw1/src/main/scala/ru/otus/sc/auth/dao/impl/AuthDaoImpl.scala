package ru.otus.sc.auth.dao.impl

import ru.otus.sc.auth.dao.AuthDao

/**
 * Auth DAO implementation
 */
class AuthDaoImpl extends AuthDao {
  private var credentials: Map[String, String] = Map()

  def saveCredentials(login: String, password: String): Boolean = {
    credentials += (login -> password)
    credentials.contains(login)
  }


  def getCredentials(login: String): Option[(String, String)] = {
    credentials.get(login) match {
      case Some(password) => Some((login, password))
      case None => None
    }
  }
}
