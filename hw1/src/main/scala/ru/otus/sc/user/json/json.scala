package ru.otus.sc.user.json

import play.api.libs.json.{Format, Json, OFormat}
import ru.otus.sc.json.AdtProtocol
import ru.otus.sc.user.model.{DeleteUserResponse, UpdateUserResponse, User}

trait UserJsonProtocol extends AdtProtocol {
  implicit lazy val userFormat: OFormat[User] = Json.format

  implicit lazy val updateUserResponseFormat: Format[UpdateUserResponse] = {
    implicit val updatedFormat: OFormat[UpdateUserResponse.Updated]   = Json.format
    implicit val notFoundFormat: OFormat[UpdateUserResponse.NotFound] = Json.format
    implicit val requireIdFormat: OFormat[UpdateUserResponse.RequireUserID.type] =
      objectFormat(UpdateUserResponse.RequireUserID)

    adtFormat("$type")(
      adtCase[UpdateUserResponse.Updated]("Updated"),
      adtCase[UpdateUserResponse.NotFound]("NotFound"),
      adtCase[UpdateUserResponse.RequireUserID.type]("RequireUserID")
    )
  }

  implicit lazy val deleteUserResponseFormat: Format[DeleteUserResponse] = {
    implicit val updatedFormat: OFormat[DeleteUserResponse.Deleted]   = Json.format
    implicit val notFoundFormat: OFormat[DeleteUserResponse.NotFound] = Json.format

    adtFormat("$type")(
      adtCase[DeleteUserResponse.Deleted]("Deleted"),
      adtCase[DeleteUserResponse.NotFound]("NotFound")
    )
  }
}

object UserJsonProtocol extends UserJsonProtocol
