package ru.otus.sc.storage.model

/**
 * Get key-value pairs in storage
 * Need when ask get all key-value association of storage
 * @param keyValues key-value pairs in storage
 */
case class StorageValuesResponse(keyValues: Map[String, String])
