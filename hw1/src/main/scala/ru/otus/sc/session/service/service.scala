package ru.otus.sc.session.service

import ru.otus.sc.session.dao.SessionDao
import ru.otus.sc.session.model.{
  SessionIdResponse,
  SessionUserRequest,
  SessionUserResponse,
  SetSessionUserRequest,
  SetSessionUserResponse
}

/**
  * Session service
  */
trait SessionService {

  /**
    * Get current session ID
    * @return current session ID
    */
  def id: SessionIdResponse

  def user(request: SetSessionUserRequest): SetSessionUserResponse

  def user(request: SessionUserRequest): SessionUserResponse
}

/**
  * Session service implementation
  * @param dao DAO for access to current session properties
  */
class SessionServiceImpl(dao: SessionDao) extends SessionService {
  def id: SessionIdResponse = SessionIdResponse(dao.id)

  override def user(request: SetSessionUserRequest): SetSessionUserResponse =
    if (dao.user(request.id, request.user)) {
      SetSessionUserResponse.Success
    } else {
      SetSessionUserResponse.AlreadySet
    }

  override def user(request: SessionUserRequest): SessionUserResponse =
    dao
      .user(request.id)
      .map(SessionUserResponse.Found)
      .getOrElse(SessionUserResponse.NotFound)
}
