package ru.otus.sc.echo.service.impl

import ru.otus.sc.echo.model.{EchoRequest, EchoResponse}
import ru.otus.sc.echo.service.EchoService


/**
 * Echo service implementation - send back whatever message have been sent to service
 */
class EchoServiceImpl extends EchoService {
  def echo(request: EchoRequest): EchoResponse = EchoResponse(request.message)
}
