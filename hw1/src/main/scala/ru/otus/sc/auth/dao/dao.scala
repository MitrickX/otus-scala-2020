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
   * @return successful?
   */
  def saveCredentials(login: String, password: String): Boolean

  /**
   * Get credentials by login
   * @param login login by which need to find credentials
   * @return login, password pair if found
   */
  def getCredentials(login: String): Option[(String, String)]
}


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
