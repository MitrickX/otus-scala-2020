package ru.otus.sc.increment.dao.impl

import ru.otus.sc.increment.dao.IncrementDao

/**
 * Implement of DAO for increment service
 * Counter live in runtime
 */
class IncrementDaoImpl extends IncrementDao {
  var count = 0

  override def increment(): Int = {
    count += 1
    count
  }
}
