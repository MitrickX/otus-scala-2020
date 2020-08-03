package ru.otus.sc.increment.service

import ru.otus.sc.increment.dao.IncrementDao
import ru.otus.sc.increment.model.IncrementResponse

/**
 * Service that increment counter
 * Response holds result counter after increment
 */
trait IncrementService {
  /**
   * Increment counter
   * @return result counter after increment
   */
  def increment(): IncrementResponse
}
/**
 * Implementation of increment service
 * @param dao DAO that holds counter and can increment it
 */
class IncrementServiceImpl(dao: IncrementDao) extends IncrementService {
  def increment(): IncrementResponse = IncrementResponse(dao.increment())
}
