package ru.otus.sc.greet.service

import ru.otus.sc.greet.dao.GreetingDao
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


/**
 * Implementation greeting service
 * If need to greet human send nice correct message back
 * Otherwise send panic message
 * @param dao DAO for greeting
 */
class GreetingServiceImpl(dao: GreetingDao) extends GreetingService {
  def greet(request: GreetRequest): GreetResponse =
    if (request.isHuman)
      GreetResponse(s"${dao.greetingPrefix} ${request.name} ${dao.greetingPostfix}")
    else GreetResponse("AAAAAAAAAA!!!!!!")
}
