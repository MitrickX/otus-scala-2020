package ru.otus.sc.auth.model

/**
 * Request to log in for auth service
 * @param login login
 * @param password password
 */
case class LogInRequest(login: String, password: String)

/**
 * Response about log in
 * Holds result of log in - success or not
 * @param result has been logging in successful?
 */
case class LoginResponse(result: Boolean)

/**
 * Request of sing up to auth service
 * @param login login
 * @param password password
 */
case class SignUpRequest(login: String, password: String)

/**
 * Response about sign up
 * Holds result sign up - success or not
 * @param result has been signing up successful?
 */
case class SignUpResponse(result: Boolean)

