package ru.otus.sc.increment.service

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
