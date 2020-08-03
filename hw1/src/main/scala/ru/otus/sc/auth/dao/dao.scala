package ru.otus.sc.auth.dao

/**
  * DAO for auth service
  * Store credentials
  */
trait AuthDao {

  /**
    * Save credentials
    * @param login login
    * @param password password
    */
  def saveCredentials(login: String, password: String): Unit

  /**
    * Get credentials by login
    * @param login login by which need to find credentials
    * @return login, password pair if found
    */
  def credentials(login: String): Option[(String, String)]

  /**
    * Exists by this login?
    * @param login login by which check existing of credentials
    * @return
    */
  def exists(login: String): Boolean
}

/**
  * Auth DAO implementation
  */
class AuthDaoImpl extends AuthDao {
  private var credentials: Map[String, String] = Map()

  def saveCredentials(login: String, password: String): Unit =
    credentials += (login -> password)

  def credentials(login: String): Option[(String, String)] =
    credentials.get(login).map(login -> _)

  def exists(login: String): Boolean = credentials.contains(login)
}
