package ru.otus.sc.increment.dao

/**
  * DAO for increment service
  */
trait IncrementDao {

  /**
    * Increment counter
    * @return incremented counter
    */
  def increment(): Unit

  /**
    * Current counter
    * @return
    */
  def value: Int
}

/**
  * Implement of DAO for increment service
  * Counter live in runtime
  */
class IncrementDaoImpl extends IncrementDao {
  private var count     = 0
  def increment(): Unit = count += 1
  def value: Int        = count
}
