package ru.otus.sc.auth.service

import ru.otus.sc.auth.dao.AuthDao
import ru.otus.sc.auth.model.{LogInRequest, LoginResponse, SignUpRequest, SignUpResponse}

trait AuthService {
  def signUp(request: SignUpRequest): SignUpResponse
  def logIn(request: LogInRequest): LoginResponse
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

  def logIn(request: LogInRequest): LoginResponse = {
    val result = dao.credentials(request.userId, request.login) match {
      case Some(credentials) => credentials._2 == request.password
      case None              => false
    }
    LoginResponse(result)
  }
}
