package ru.otus.sc.auth.service

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
