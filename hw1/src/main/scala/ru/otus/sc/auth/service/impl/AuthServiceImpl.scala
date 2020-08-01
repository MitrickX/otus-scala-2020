package ru.otus.sc.auth.service.impl

import ru.otus.sc.auth.dao.AuthDao
import ru.otus.sc.auth.model.{LogInRequest, LoginResponse, SignUpRequest, SignUpResponse}
import ru.otus.sc.auth.service.AuthService

/**
 * Concrete implementation of auth service
 * @param dao DAO for store credentials
 */
class AuthServiceImpl(dao: AuthDao) extends AuthService {

  def signUp(request: SignUpRequest): SignUpResponse = {
    val result = dao.getCredentials(request.login) match {
      case Some(_) => false
      case None => dao.saveCredentials(request.login, request.password)
    }
    SignUpResponse(result)
  }

  def logIn(request: LogInRequest): LoginResponse = {
    val result = dao.getCredentials(request.login) match {
      case Some(credentials) => credentials._2 == request.password
      case None => false
    }
    LoginResponse(result)
  }
}
