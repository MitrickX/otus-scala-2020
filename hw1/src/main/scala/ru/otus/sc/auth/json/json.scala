package ru.otus.sc.auth.json

import play.api.libs.json.{Format, Json, OFormat}
import ru.otus.sc.auth.model.{SignUpRequest, SignUpResponse}
import ru.otus.sc.json.AdtProtocol

trait AuthJsonProtocol extends AdtProtocol {
  implicit lazy val signUpRequestForm: OFormat[SignUpRequest] = Json.format

  implicit lazy val signUpResponseFormat: Format[SignUpResponse] = {
    implicit val alreadySignedUpFormat: OFormat[SignUpResponse.AlreadySignedUp.type] =
      objectFormat(SignUpResponse.AlreadySignedUp)
    implicit val SuccessFormat: OFormat[SignUpResponse.Success.type] =
      objectFormat(SignUpResponse.Success)
    implicit val FailFormat: OFormat[SignUpResponse.Fail.type] =
      objectFormat(SignUpResponse.Fail)

    adtFormat("$type")(
      adtCase[SignUpResponse.AlreadySignedUp.type]("AlreadySignedUp"),
      adtCase[SignUpResponse.Success.type]("Success"),
      adtCase[SignUpResponse.Fail.type]("Fail")
    )
  }
}

object AuthJsonProtocol extends AuthJsonProtocol
