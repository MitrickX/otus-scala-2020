package ru.otus.sc.greet.dao

/**
 * DAO for greeting service
 */
trait GreetingDao {
  /**
   * Prefix in greeting message
   * @return
   */
  def greetingPrefix: String

  /**
   * Suffix in greeting message
   * @return
   */
  def greetingPostfix: String
}

/**
 * Greeting DAO implementation
 */
class GreetingDaoImpl extends GreetingDao {
  val greetingPrefix: String  = "Hi"
  val greetingPostfix: String = "!"
}
