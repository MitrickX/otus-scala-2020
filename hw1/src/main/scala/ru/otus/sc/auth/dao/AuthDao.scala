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
