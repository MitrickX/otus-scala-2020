package ru.otus.sc.echo.model

/**
 * Response of echo service
 * @param message original message that have been sent to echo service
 */
case class EchoResponse(message: String)
