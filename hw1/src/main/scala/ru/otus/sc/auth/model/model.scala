package ru.otus.sc.auth.model
import java.util.UUID

case class LogInRequest(login: String, password: String)
sealed trait LogInResponse
object LogInResponse {
  case class Success(userId: UUID) extends LogInResponse
  case object Fail                 extends LogInResponse
}

case class SignUpRequest(userId: UUID, login: String, password: String)

sealed trait SignUpResponse
object SignUpResponse {
  case object AlreadySignedUp extends SignUpResponse
  case object Success         extends SignUpResponse
  case object Fail            extends SignUpResponse

}
