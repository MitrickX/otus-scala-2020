package ru.otus.sc.auth.model

/**
 * Request to log in for auth service
 * @param login login
 * @param password password
 */
case class LogInRequest(login: String, password: String)
