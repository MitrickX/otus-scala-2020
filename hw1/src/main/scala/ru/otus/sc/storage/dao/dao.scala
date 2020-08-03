package ru.otus.sc.storage.dao

/**
 * DAO to works with key-value storage
 */
trait StorageDao {
  /**
   * Get all keys that available in this storage
   * @return
   */
  def availableKeys: List[String]

  /**
   * Get value by key currently saved in storage
   * @param key key by with get value
   * @return value associated with key
   */
  def value(key: String): Option[String]

  /**
   * Set value by key
   * @param key key to associate with value
   * @param value value to associate with key
   */
  def value(key: String, value: String): Unit

  /**
   * Get all key-value pairs currently saved in storage
   * @return all key-value associations
   */
  def allValues: Map[String, String]
}

/**
 * Implementation of DAO to key-value storage
 */
class StorageDaoImpl extends StorageDao {
  private var storage: Map[String, String] = Map()

  val availableKeys = List("version", "import", "package")

  def value(key: String): Option[String] = {
    val allowed = availableKeys.indexOf(key) >= 0
    if (allowed) storage.get(key)
    else None
  }

  def value(key: String, value: String): Unit = {
    val allowed = availableKeys.indexOf(key) >= 0
    if (allowed) storage += (key -> value)
  }

  def allValues: Map[String, String] = storage
}
