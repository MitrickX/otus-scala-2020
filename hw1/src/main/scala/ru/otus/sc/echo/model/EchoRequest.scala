package ru.otus.sc.echo.model

/**
 * Request to echo service
 * @param message message to send back
 */
case class EchoRequest(message: String)
