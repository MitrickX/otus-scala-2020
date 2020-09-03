package ru.otus.sc.auth.json

import play.api.libs.json.{Format, Json, OFormat}
import ru.otus.sc.auth.model.{LogInRequest, LogInResponse, SignUpRequest, SignUpResponse}
import ru.otus.sc.json.AdtProtocol

trait AuthJsonProtocol extends AdtProtocol {
  implicit lazy val signUpRequestFormat: OFormat[SignUpRequest] = Json.format

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

  implicit lazy val logInRequestFormat: OFormat[LogInRequest] = Json.format
  implicit lazy val loginResponseFormat: Format[LogInResponse] = {
    implicit val SuccessFormat: OFormat[LogInResponse.Success] = Json.format
    implicit val FailFormat: OFormat[LogInResponse.Fail.type] =
      objectFormat(LogInResponse.Fail)

    adtFormat("$type")(
      adtCase[LogInResponse.Success]("Success"),
      adtCase[LogInResponse.Fail.type]("Fail")
    )
  }
}

object AuthJsonProtocol extends AuthJsonProtocol
