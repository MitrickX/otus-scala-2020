package ru.otus.sc.auth.service

import java.util.UUID

import ru.otus.sc.auth.dao.AuthDao
import ru.otus.sc.auth.model.{LogInRequest, LogInResponse, SignUpRequest, SignUpResponse}

trait AuthService {
  def signUp(request: SignUpRequest): SignUpResponse
  def logIn(request: LogInRequest): LogInResponse
}

class AuthServiceImpl(dao: AuthDao) extends AuthService {

  def signUp(request: SignUpRequest): SignUpResponse = {
    if (dao.exists(request.userId, request.login)) {
      SignUpResponse.AlreadySignedUp
    } else {
      dao.saveCredentials(request.userId, request.login, request.password)
      if (dao.exists(request.userId, request.login)) {
        SignUpResponse.Success
      } else {
        SignUpResponse.Fail
      }
    }
  }

  def logIn(request: LogInRequest): LogInResponse = {
    def login(userId: UUID): LogInResponse =
      dao.credentials(userId, request.login) match {
        case Some(credentials) =>
          if (credentials._2 == request.password) {
            LogInResponse.Success(userId)
          } else {
            LogInResponse.Fail
          }
        case None => LogInResponse.Fail
      }

    dao.findByLogin(request.login) match {
      case Some(userId) => login(userId)
      case None         => LogInResponse.Fail
    }

  }
}
