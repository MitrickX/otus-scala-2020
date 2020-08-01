package ru.otus.sc.storage.model

/**
 * Response that holds list of keys
 * Need when ask for all available keys in storage
 * @param keys list of keys
 */
case class StorageKeysResponse(keys: List[String])
