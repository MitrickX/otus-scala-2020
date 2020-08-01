package ru.otus.sc.storage.model

/**
 * Response that holds value from storage
 * @param value value from storage if existed
 */
case class StorageValueResponse(value: Option[String])
