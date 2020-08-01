package ru.otus.sc.greet.service

import ru.otus.sc.greet.model.{GreetRequest, GreetResponse}

/**
 * Greeting service
 */
trait GreetingService {
  /**
   * Greet someone
   * @param request request for greeting
   * @return
   */
  def greet(request: GreetRequest): GreetResponse
}
