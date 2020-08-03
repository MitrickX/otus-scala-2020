package ru.otus.sc.auth.service

import ru.otus.sc.auth.dao.AuthDao
import ru.otus.sc.auth.model.{LogInRequest, LoginResponse, SignUpRequest, SignUpResponse}

/**
  * Auth service
  */
trait AuthService {

  /**
    * Sign up
    * @param request holds credentials
    * @return sign up result
    */
  def signUp(request: SignUpRequest): SignUpResponse

  /**
    * Log in
    * @param request holds credentials
    * @return log in result
    */
  def logIn(request: LogInRequest): LoginResponse
}

/**
  * Concrete implementation of auth service
  * @param dao DAO for store credentials
  */
class AuthServiceImpl(dao: AuthDao) extends AuthService {

  def signUp(request: SignUpRequest): SignUpResponse = {
    val result = dao.credentials(request.login) match {
      case Some(_) => false
      case None =>
        dao.saveCredentials(request.login, request.password)
        dao.exists(request.login)
    }
    SignUpResponse(result)
  }

  def logIn(request: LogInRequest): LoginResponse = {
    val result = dao.credentials(request.login) match {
      case Some(credentials) => credentials._2 == request.password
      case None              => false
    }
    LoginResponse(result)
  }
}
