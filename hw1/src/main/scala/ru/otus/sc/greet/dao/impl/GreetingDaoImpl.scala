package ru.otus.sc.greet.dao.impl

import ru.otus.sc.greet.dao.GreetingDao

/**
 * Greeting DAO implementation
 */
class GreetingDaoImpl extends GreetingDao {
  val greetingPrefix: String  = "Hi"
  val greetingPostfix: String = "!"
}
