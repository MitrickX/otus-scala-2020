package ru.otus.sc

import ru.otus.sc.auth.dao.impl.AuthDaoImpl
import ru.otus.sc.auth.model.{LogInRequest, LoginResponse, SignUpRequest, SignUpResponse}
import ru.otus.sc.auth.service.AuthService
import ru.otus.sc.auth.service.impl.AuthServiceImpl
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

/**
 * Application interface
 */
trait App {
  /**
   * Greeting service
   * @param request request to greeting service
   * @return greeting response
   */
  def greet(request: GreetRequest): GreetResponse

  /**
   * Echo service
   * @param request request to echo service
   * @return echo response
   */
  def echo(request: EchoRequest): EchoResponse

  /**
   * Increment service
   * @return response of increment service
   */
  def increment(): IncrementResponse

  /**
   * Get available storage keys that available in this application
   * @return response with list of keys
   */
  def getAvailableStorageKeys: StorageKeysResponse

  /**
   * Get value from storage
   * @param request request to storage service to getting value
   * @return response from storage service
   */
  def getStorageValue(request: StorageValueRequest): StorageValueResponse

  /**
   * Set value in storage
   * @param request request to set value in storage
   * @return response from storage service
   */
  def setStorageValue(request: SetStorageValueRequest): SetStorageValueResponse

  /**
   * Get all pairs currently saved in storage
   * @return response from storage service
   */
  def getAllStorageValues: StorageValuesResponse

  /**
   * Get current session ID of application
   * @return
   */
  def getSessionId: SessionIdResponse

  /**
   * Sign up in application
   * @param request request for singing up
   * @return response about signing up
   */
  def signUp(request: SignUpRequest): SignUpResponse

  /**
   * Log in to application
   * @param request request to logging in
   * @return response about logging in
   */
  def logIn(request: LogInRequest): LoginResponse
}

/**
 * Concrete application implementation
 */
object App {
  private class AppImpl(greeting: GreetingService,
                        echo: EchoService,
                        increment: IncrementService,
                        storage: StorageService,
                        session: SessionService,
                        auth: AuthService) extends App {

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

    def signUp(request: SignUpRequest): SignUpResponse = auth.signUp(request)
    def logIn(request: LogInRequest): LoginResponse = auth.logIn(request)
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

    val authDao = new AuthDaoImpl
    val authService = new AuthServiceImpl(authDao)

    new AppImpl(
      greeting = greetingService,
      echo = echoService,
      increment = incrementService,
      storage = storageService,
      session = sessionService,
      auth = authService
    )
  }
}
