package ru.otus.sc.user.dao

import org.scalamock.scalatest.MockFactory
import org.scalatest.freespec.AnyFreeSpec
import ru.otus.sc.storage.dao.StorageDaoImpl
import org.scalatest.matchers.should.Matchers._

class StorageDaoImplSpec extends AnyFreeSpec with MockFactory {
  "StorageDaoImpl tests" - {
    "set value" - {

      "should set value by available key" in {
        val dao   = new StorageDaoImpl()
        val key   = dao.availableKeys.head
        val value = "123"

        dao.value(key, value)

        dao.value(key).get shouldBe value
      }

      "should not set value by not available key" in {
        val dao   = new StorageDaoImpl()
        val key   = "abc"
        val value = "123"

        dao.value(key, value)

        dao.value(key) shouldBe None
      }
    }

    "get value" - {

      "should get none" in {
        val dao = new StorageDaoImpl()
        val key = dao.availableKeys.head

        dao.value(key) shouldBe None
      }

    }

    "get all values" - {

      "should get empty list of key-value" in {
        val dao = new StorageDaoImpl()
        dao.allValues.size shouldBe 0
      }

      "should get not empty list of key-value" in {
        val dao  = new StorageDaoImpl()
        val key1 = dao.availableKeys.head
        val key2 = dao.availableKeys.lift(1).get

        val value1 = "xyz"
        val value2 = "123"

        dao.value(key1, value1)
        dao.value(key2, value2)

        dao.allValues.size shouldBe 2
      }
    }
  }
}
