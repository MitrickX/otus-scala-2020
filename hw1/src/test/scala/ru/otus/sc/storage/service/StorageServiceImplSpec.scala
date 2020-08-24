package ru.otus.sc.storage.service

import org.scalamock.scalatest.MockFactory
import org.scalatest.freespec.AnyFreeSpec
import ru.otus.sc.storage.dao.StorageDaoImpl
import org.scalatest.matchers.should.Matchers._
import ru.otus.sc.storage.model.{
  SetStorageValueRequest,
  SetStorageValueResponse,
  StorageValueRequest,
  StorageValueResponse,
  StorageValuesResponse
}

class StorageServiceImplSpec extends AnyFreeSpec with MockFactory {
  "StorageServiceImpl tests" - {
    "set value" - {

      "should set value by available key" in {
        val dao   = mock[StorageDaoImpl]
        val srv   = new StorageServiceImpl(dao)
        val key   = "abc"
        val value = "123"

        (dao.value: String => Option[String]).expects(key).returns(Some(value))
        (dao.value: (String, String) => Unit).expects(key, value).returns()

        srv.value(SetStorageValueRequest(key, value)) shouldBe SetStorageValueResponse(true)
      }

      "should not set value by not available key" in {
        val dao   = mock[StorageDaoImpl]
        val srv   = new StorageServiceImpl(dao)
        val key   = "xyz"
        val value = "123"

        (dao.value: String => Option[String]).expects(key).returns(None)
        (dao.value: (String, String) => Unit).expects(key, value).returns()

        srv.value(SetStorageValueRequest(key, value)) shouldBe SetStorageValueResponse(false)
      }
    }

    "get value" - {

      "should get none" in {
        val dao = mock[StorageDaoImpl]
        val srv = new StorageServiceImpl(dao)
        val key = "xyz"

        (dao.value: String => Option[String]).expects(key).returns(None)

        srv.value(StorageValueRequest(key)) shouldBe StorageValueResponse(None)
      }

      "should get some" in {
        val dao   = mock[StorageDaoImpl]
        val srv   = new StorageServiceImpl(dao)
        val key   = "xyz"
        val value = "123"

        (dao.value: String => Option[String]).expects(key).returns(Some(value))

        srv.value(StorageValueRequest(key)) shouldBe StorageValueResponse(Some(value))
      }

    }

    "get all values" - {

      "should get empty list of key-value" in {
        val dao = mock[StorageDaoImpl]
        val srv = new StorageServiceImpl(dao)

        (dao.allValues _).expects().returns(Map.empty)

        srv.allValues shouldBe StorageValuesResponse(Map.empty)
      }

      "should get not empty list of key-value" in {
        val dao                            = mock[StorageDaoImpl]
        val srv                            = new StorageServiceImpl(dao)
        val keyValues: Map[String, String] = Map("abc" -> "123", "xay" -> "3s2")

        (dao.allValues _).expects().returns(keyValues)

        srv.allValues shouldBe StorageValuesResponse(keyValues)
      }
    }
  }
}
