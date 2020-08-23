package ru.otus.sc.auth.model
import ru.otus.sc.user.model.User

case class LogInRequest(user: User, login: String, password: String)
case class LoginResponse(result: Boolean)
case class SignUpRequest(user: User, login: String, password: String)
case class SignUpResponse(result: Boolean)
