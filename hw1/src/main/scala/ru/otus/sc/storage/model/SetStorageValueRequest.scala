package ru.otus.sc.storage.model

/**
 * Request for set value in storage
 * @param key key to associate with value
 * @param value value to associate with key
 */
case class SetStorageValueRequest(key: String, value: String)
