package ru.otus.sc.user.model

import java.util.UUID

case class User(id: Option[UUID], firstName: String, lastName: String, age: Int)

case class CreateUserRequest(user: User)
case class CreateUserResponse(user: User)

case class DeleteUserRequest(id: UUID)

sealed trait DeleteUserResponse
object DeleteUserResponse {
  case class Deleted(user: User) extends DeleteUserResponse
  case class NotFound(id: UUID)  extends DeleteUserResponse
}

case class UserRequest(id: UUID)

sealed trait UserResponse
object UserResponse {
  case class Found(user: User)  extends UserResponse
  case class NotFound(id: UUID) extends UserResponse
}

case class UpdateUserRequest(user: User)

sealed trait UpdateUserResponse
object UpdateUserResponse {
  case class Updated(user: User) extends UpdateUserResponse
  case class NotFound(id: UUID)  extends UpdateUserResponse
  case object RequireUserID      extends UpdateUserResponse
}

sealed trait FindUsersRequest
object FindUsersRequest {
  case class ByFirstName(fistName: String)               extends FindUsersRequest
  case class ByLastName(lastName: String)                extends FindUsersRequest
  case class ByName(firstName: String, lastName: String) extends FindUsersRequest
  case class ByAge(from: Int, to: Int)                   extends FindUsersRequest
  case class Find(
      firstName: Option[String] = None,
      lastName: Option[String] = None,
      from: Option[Int],
      to: Option[Int]
  )               extends FindUsersRequest
  case object All extends FindUsersRequest
}

case class FindUsersResponse(users: Seq[User])
