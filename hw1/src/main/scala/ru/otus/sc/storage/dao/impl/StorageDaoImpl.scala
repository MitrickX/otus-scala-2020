package ru.otus.sc.storage.dao.impl

import ru.otus.sc.storage.dao.StorageDao

/**
 * Implementation of DAO to key-value storage
 */
class StorageDaoImpl extends StorageDao {
  private var storage: Map[String, String] = Map()

  val getAvailableKeys = List("version", "import", "package")

  def getValue(key: String): Option[String] = {
    val allowed = getAvailableKeys.indexOf(key) >= 0
    if (allowed) storage.get(key)
    else None
  }

  def setValue(key: String, value: String): Boolean = {
    val allowed = getAvailableKeys.indexOf(key) >= 0
    if (allowed) storage += (key -> value)
    allowed
  }

  def getAllValues: Map[String, String] = storage
}
