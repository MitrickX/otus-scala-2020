package ru.otus.sc.greet.model

/**
 * Request to greeting service for greeting
 * @param name name for whom address greeting
 * @param isHuman is greeting human
 */
case class GreetRequest(name: String, isHuman: Boolean = true)
