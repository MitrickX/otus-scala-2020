package ru.otus.sc.session.service

import ru.otus.sc.session.dao.SessionDao
import ru.otus.sc.session.model.SessionIdResponse

/**
 * Session service
 */
trait SessionService {
  /**
   * Get current session ID
   * @return current session ID
   */
  def id: SessionIdResponse
}

/**
 * Session service implementation
 * @param dao DAO for access to current session properties
 */
class SessionServiceImpl(dao: SessionDao) extends SessionService {
  def id: SessionIdResponse = SessionIdResponse(dao.id)
}
