package ru.otus.sc.session.service

import ru.otus.sc.session.model.SessionIdResponse

/**
 * Session service
 */
trait SessionService {
  /**
   * Get current session ID
   * @return current session ID
   */
  def getId: SessionIdResponse
}
