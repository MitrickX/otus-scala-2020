package ru.otus.sc.increment.dao

/**
 * DAO for increment service
 */
trait IncrementDao {
  /**
   * Increment counter
   * @return incremented counter
   */
  def increment(): Int
}

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
