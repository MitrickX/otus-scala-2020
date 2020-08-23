package ru.otus.sc.user.service

import ru.otus.sc.user.model.{
  CreateUserRequest,
  CreateUserResponse,
  DeleteUserRequest,
  DeleteUserResponse,
  FindUsersRequest,
  FindUsersResponse,
  GetUserRequest,
  GetUserResponse,
  UpdateUserRequest,
  UpdateUserResponse
}

import scala.ru.otus.sc.user.dao.UserDao

trait UserService {
  def createUser(request: CreateUserRequest): CreateUserResponse
  def getUser(request: GetUserRequest): GetUserResponse
  def updateUser(request: UpdateUserRequest): UpdateUserResponse
  def deleteUser(request: DeleteUserRequest): DeleteUserResponse
  def findUsers(request: FindUsersRequest): FindUsersResponse
}
class UserServiceImpl(dao: UserDao) extends UserService {
  override def createUser(request: CreateUserRequest): CreateUserResponse =
    CreateUserResponse(dao.createUser(request.user))

  override def getUser(request: GetUserRequest): GetUserResponse =
    dao
      .getUser(request.id)
      .map(GetUserResponse.Found)
      .getOrElse(GetUserResponse.NotFound(request.id))

  override def updateUser(request: UpdateUserRequest): UpdateUserResponse =
    request.user.id match {
      case None => UpdateUserResponse.RequireUserID
      case Some(id) =>
        dao.updateUser(request.user) match {
          case Some(user) => UpdateUserResponse.Updated(user)
          case None       => UpdateUserResponse.NotFound(id)
        }
    }

  override def deleteUser(request: DeleteUserRequest): DeleteUserResponse =
    dao
      .deleteUser(request.id)
      .map(DeleteUserResponse.Deleted)
      .getOrElse(DeleteUserResponse.NotFound(request.id))

  override def findUsers(request: FindUsersRequest): FindUsersResponse =
    request match {
      case FindUsersRequest.ByFirstName(firstName) =>
        FindUsersResponse(dao.findByFirstName(firstName))
      case FindUsersRequest.ByName(firstName, lastName) =>
        FindUsersResponse(dao.findByName(firstName, lastName))
      case FindUsersRequest.ByLastName(lastName) => FindUsersResponse(dao.findByLastName(lastName))
      case FindUsersRequest.ByAge(from, to)      => FindUsersResponse(dao.findByAge(from, to))
      case FindUsersRequest.All                  => FindUsersResponse(dao.allUsers)
    }
}
