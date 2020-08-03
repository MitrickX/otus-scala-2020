package ru.otus.sc.storage.model

/**
 * Request for set value in storage
 * @param key key to associate with value
 * @param value value to associate with key
 */
case class SetStorageValueRequest(key: String, value: String)

/**
 * Response on set storage value request
 * @param result have been value associate?
 */
case class SetStorageValueResponse(result: Boolean)

/**
 * Response that holds list of keys
 * Need when ask for all available keys in storage
 * @param keys list of keys
 */
case class StorageKeysResponse(keys: List[String])

/**
 * Request for value associated with key
 * @param key key to get value associated with
 */
case class StorageValueRequest(key: String)

/**
 * Response that holds value from storage
 * @param value value from storage if existed
 */
case class StorageValueResponse(value: Option[String])

/**
 * Get key-value pairs in storage
 * Need when ask get all key-value association of storage
 * @param keyValues key-value pairs in storage
 */
case class StorageValuesResponse(keyValues: Map[String, String])