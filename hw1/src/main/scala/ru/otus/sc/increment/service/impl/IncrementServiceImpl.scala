package ru.otus.sc.increment.service.impl

import ru.otus.sc.increment.dao.IncrementDao
import ru.otus.sc.increment.model.IncrementResponse
import ru.otus.sc.increment.service.IncrementService

/**
 * Implementation of increment service
 * @param dao DAO that holds counter and can increment it
 */
class IncrementServiceImpl(dao: IncrementDao) extends IncrementService {
  def increment(): IncrementResponse = IncrementResponse(dao.increment())
}
