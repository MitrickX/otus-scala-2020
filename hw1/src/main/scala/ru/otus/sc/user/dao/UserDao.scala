package ru.otus.sc.user.dao

import java.util.UUID

import ru.otus.sc.user.model.User

trait UserDao {
  def createUser(user: User): User
  def getUser(id: UUID): Option[User]
  def updateUser(user: User): Option[User]
  def deleteUser(id: UUID): Option[User]
  def findByFirstName(firstName: String): Seq[User]
  def findByLastName(lastName: String): Seq[User]
  def findByName(firstName: String, lastName: String): Seq[User]
  def findByAge(from: Int, to: Int): Seq[User]
  def allUsers: Seq[User]
}

class UserDaoImpl extends UserDao {
  private var users: Map[UUID, User] = Map.empty

  override def createUser(user: User): User = {
    val id      = UUID.randomUUID()
    val newUser = user.copy(id = Some(id))
    users += (id -> newUser)
    newUser
  }

  override def getUser(id: UUID): Option[User] = users.get(id)

  override def updateUser(user: User): Option[User] =
    for {
      id <- user.id
      _  <- users.get(id)
    } yield {
      users += (id -> user)
      user
    }

  override def deleteUser(id: UUID): Option[User] =
    for {
      user <- users.get(id)
    } yield {
      users -= id
      user
    }

  override def findByFirstName(firstName: String): Seq[User] =
    users.values.filter(_.firstName == firstName).toVector

  override def findByLastName(lastName: String): Seq[User] =
    users.values.filter(_.lastName == lastName).toVector

  override def findByName(firstName: String, lastName: String): Seq[User] =
    users.values.filter(user => user.firstName == firstName && user.lastName == lastName).toVector

  override def findByAge(from: Int, to: Int): Seq[User] =
    users.values.filter(user => user.age >= from && user.age <= to).toVector

  override def allUsers: Seq[User] = users.values.toVector
}
