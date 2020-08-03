package ru.otus.sc.storage.service

import ru.otus.sc.storage.dao.StorageDao
import ru.otus.sc.storage.model._

/**
 * Storage service for associate values with keys
 */
trait StorageService {
  /**
   * Get all available keys supported by storage
   * @return response that holds keys
   */
  def availableKeys: StorageKeysResponse

  /**
   * Get concrete value
   * @param request holds key to get value associated with
   * @return response that holds value if exists
   */
  def value(request: StorageValueRequest): StorageValueResponse

  /**
   * Associate concrete key with concrete value
   * @param request holds key and value
   * @return response that says have need value saved in storage
   */
  def value(request: SetStorageValueRequest): SetStorageValueResponse

  /**
   * Get all key-value pairs currently in storage
   * @return key-value pairs
   */
  def allValues: StorageValuesResponse
}

/**
 * Concrete implementation of storage service
 * @param dao DAO for work with storage
 */
class StorageServiceImpl(dao: StorageDao) extends StorageService {
  val availableKeys: StorageKeysResponse = StorageKeysResponse(dao.availableKeys)

  def value(request: StorageValueRequest): StorageValueResponse =
    StorageValueResponse(dao.value(request.key))

  def value(request: SetStorageValueRequest): SetStorageValueResponse = {
    dao.value(request.key, request.value)
    SetStorageValueResponse(dao.value(request.key).isDefined)
  }

  def allValues: StorageValuesResponse = StorageValuesResponse(dao.allValues)
}
