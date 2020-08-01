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
