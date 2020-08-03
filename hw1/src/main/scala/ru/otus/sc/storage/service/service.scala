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
  def getAvailableKeys: StorageKeysResponse

  /**
   * Get concrete value
   * @param request holds key to get value associated with
   * @return response that holds value if exists
   */
  def getValue(request: StorageValueRequest): StorageValueResponse

  /**
   * Associate concrete key with concrete value
   * @param request holds key and value
   * @return response that says have need value saved in storage
   */
  def setValue(request: SetStorageValueRequest): SetStorageValueResponse

  /**
   * Get all key-value pairs currently in storage
   * @return key-value pairs
   */
  def getAllValues: StorageValuesResponse
}

/**
 * Concrete implementation of storage service
 * @param dao DAO for work with storage
 */
class StorageServiceImpl(dao: StorageDao) extends StorageService {
  val getAvailableKeys: StorageKeysResponse = StorageKeysResponse(dao.getAvailableKeys)

  def getValue(request: StorageValueRequest): StorageValueResponse =
    StorageValueResponse(dao.getValue(request.key))

  def setValue(request: SetStorageValueRequest): SetStorageValueResponse =
    SetStorageValueResponse(dao.setValue(request.key, request.value))

  def getAllValues: StorageValuesResponse = StorageValuesResponse(dao.getAllValues)
}
