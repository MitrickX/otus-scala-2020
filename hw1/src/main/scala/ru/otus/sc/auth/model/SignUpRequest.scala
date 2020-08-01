package ru.otus.sc.auth.model

/**
 * Request of sing up to auth service
 * @param login login
 * @param password password
 */
case class SignUpRequest(login: String, password: String)
