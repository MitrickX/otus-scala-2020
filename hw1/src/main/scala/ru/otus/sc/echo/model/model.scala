package ru.otus.sc.echo.model

/**
 * Request to echo service
 * @param message message to send back
 */
case class EchoRequest(message: String)

/**
 * Response of echo service
 * @param message original message that have been sent to echo service
 */
case class EchoResponse(message: String)
