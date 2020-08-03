package ru.otus.sc.echo.service

import ru.otus.sc.echo.model.{EchoRequest, EchoResponse}

/**
 * Echo service
 */
trait EchoService {
  /**
   * Send back whatever will be in request
   * @param request request that holds message
   * @return response with same message
   */
  def echo(request: EchoRequest): EchoResponse
}

/**
 * Echo service implementation - send back whatever message have been sent to service
 */
class EchoServiceImpl extends EchoService {
  def echo(request: EchoRequest): EchoResponse = EchoResponse(request.message)
}
