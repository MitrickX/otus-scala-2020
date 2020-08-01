package ru.otus.sc.storage.service.impl

import ru.otus.sc.storage.dao.StorageDao
import ru.otus.sc.storage.model._
import ru.otus.sc.storage.service.StorageService

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
