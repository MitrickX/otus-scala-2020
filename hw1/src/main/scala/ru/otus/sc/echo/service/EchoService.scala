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
