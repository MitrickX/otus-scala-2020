package ru.otus.sc.session.service.impl

import ru.otus.sc.session.dao.SessionDao
import ru.otus.sc.session.model.SessionIdResponse
import ru.otus.sc.session.service.SessionService

/**
 * Session service implementation
 * @param dao DAO for access to current session properties
 */
class SessionServiceImpl(dao: SessionDao) extends SessionService {
  def getId: SessionIdResponse = SessionIdResponse(dao.getId)
}
