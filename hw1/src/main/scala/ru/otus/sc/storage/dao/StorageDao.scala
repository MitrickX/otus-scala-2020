package ru.otus.sc.storage.dao

/**
 * DAO to works with key-value storage
 */
trait StorageDao {
  /**
   * Get all keys that available in this storage
   * @return
   */
  def getAvailableKeys: List[String]

  /**
   * Get value by key currently saved in storage
   * @param key key by with get value
   * @return value associated with key
   */
  def getValue(key: String): Option[String]

  /**
   * Set value by key
   * @param key key to associate with value
   * @param value value to associate with key
   * @return if key is not available for current storage returns false otherwise true
   */
  def setValue(key: String, value: String): Boolean

  /**
   * Get all key-value pairs currently saved in storage
   * @return all key-value associations
   */
  def getAllValues: Map[String, String]
}