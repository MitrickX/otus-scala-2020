package ru.otus.sc.session.model

import java.util.UUID

import ru.otus.sc.user.model.User

case class SessionIdResponse(id: UUID)

case class SessionUserRequest(id: UUID)

sealed trait SessionUserResponse
object SessionUserResponse {
  case class Found(user: User) extends SessionUserResponse
  case object NotFound         extends SessionUserResponse
}

case class SetSessionUserRequest(id: UUID, user: User)

sealed trait SetSessionUserResponse
object SetSessionUserResponse {
  case object AlreadySet extends SetSessionUserResponse
  case object Success    extends SetSessionUserResponse
}
