package ru.otus.sc.auth.service

import ru.otus.sc.auth.dao.AuthDao
import ru.otus.sc.auth.model.{LogInRequest, LoginResponse, SignUpRequest, SignUpResponse}

trait AuthService {
  def signUp(request: SignUpRequest): SignUpResponse
  def logIn(request: LogInRequest): LoginResponse
}

class AuthServiceImpl(dao: AuthDao) extends AuthService {

  def signUp(request: SignUpRequest): SignUpResponse = {
    val result = dao.credentials(request.user, request.login) match {
      case Some(_) => false
      case None =>
        dao.saveCredentials(request.user, request.login, request.password)
        dao.exists(request.user, request.login)
    }
    SignUpResponse(result)
  }

  def logIn(request: LogInRequest): LoginResponse = {
    val result = dao.credentials(request.user, request.login) match {
      case Some(credentials) => credentials._2 == request.password
      case None              => false
    }
    LoginResponse(result)
  }
}
