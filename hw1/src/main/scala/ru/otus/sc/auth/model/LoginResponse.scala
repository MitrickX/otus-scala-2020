package ru.otus.sc.auth.model

/**
 * Response about log in
 * Holds result of log in - success or not
 * @param result has been logging in successful?
 */
case class LoginResponse(result: Boolean)
