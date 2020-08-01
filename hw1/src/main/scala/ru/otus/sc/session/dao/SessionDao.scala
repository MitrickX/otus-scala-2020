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
