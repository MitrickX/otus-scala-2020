package ru.otus.sc.user.json

import play.api.libs.json.{Json, OFormat}
import ru.otus.sc.user.model.User

trait UserJsonProtocol {
  implicit lazy val userFormat: OFormat[User] = Json.format
}

object UserJsonProtocol extends UserJsonProtocol
