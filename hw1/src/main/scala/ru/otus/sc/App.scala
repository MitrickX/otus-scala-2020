package ru.otus.sc

import ru.otus.sc.echo.model.{EchoRequest, EchoResponse}
import ru.otus.sc.echo.service.EchoService
import ru.otus.sc.echo.service.impl.EchoServiceImpl
import ru.otus.sc.greet.dao.impl.GreetingDaoImpl
import ru.otus.sc.greet.model.{GreetRequest, GreetResponse}
import ru.otus.sc.greet.service.GreetingService
import ru.otus.sc.greet.service.impl.GreetingServiceImpl
import ru.otus.sc.increment.dao.impl.IncrementDaoImpl
import ru.otus.sc.increment.model.IncrementResponse
import ru.otus.sc.increment.service.IncrementService
import ru.otus.sc.increment.service.impl.IncrementServiceImpl
import ru.otus.sc.session.dao.impl.SessionDaoImpl
import ru.otus.sc.session.model.SessionIdResponse
import ru.otus.sc.session.service.SessionService
import ru.otus.sc.session.service.impl.SessionServiceImpl
import ru.otus.sc.storage.dao.impl.StorageDaoImpl
import ru.otus.sc.storage.model._
import ru.otus.sc.storage.service.StorageService
import ru.otus.sc.storage.service.impl.StorageServiceImpl

trait App {
  def greet(request: GreetRequest): GreetResponse
  def echo(request: EchoRequest): EchoResponse
  def increment(): IncrementResponse

  def getAvailableStorageKeys: StorageKeysResponse
  def getStorageValue(request: StorageValueRequest): StorageValueResponse
  def setStorageValue(request: SetStorageValueRequest): SetStorageValueResponse
  def getAllStorageValues: StorageValuesResponse

  def getSessionId: SessionIdResponse
}

object App {
  private class AppImpl(greeting: GreetingService,
                        echo: EchoService,
                        increment: IncrementService,
                        storage: StorageService,
                        session: SessionService) extends App {

    def greet(request: GreetRequest): GreetResponse = greeting.greet(request)
    def echo(request: EchoRequest): EchoResponse = echo.echo(request)
    def increment(): IncrementResponse = increment.increment()

    val getAvailableStorageKeys: StorageKeysResponse = storage.getAvailableKeys

    def getStorageValue(request: StorageValueRequest): StorageValueResponse =
      storage.getValue(request)
    def setStorageValue(request: SetStorageValueRequest): SetStorageValueResponse =
      storage.setValue(request)

    def getAllStorageValues: StorageValuesResponse = storage.getAllValues

    def getSessionId: SessionIdResponse = session.getId
  }

  def apply(): App = {
    val greetingDao     = new GreetingDaoImpl
    val greetingService = new GreetingServiceImpl(greetingDao)

    val echoService = new EchoServiceImpl()

    val incrementDao = new IncrementDaoImpl
    val incrementService = new IncrementServiceImpl(incrementDao)

    val storageDao = new StorageDaoImpl
    val storageService = new StorageServiceImpl(storageDao)

    val sessionDao = new SessionDaoImpl
    val sessionService = new SessionServiceImpl(sessionDao)

    new AppImpl(
      greeting = greetingService,
      echo = echoService,
      increment = incrementService,
      storage = storageService,
      session = sessionService
    )
  }
}
