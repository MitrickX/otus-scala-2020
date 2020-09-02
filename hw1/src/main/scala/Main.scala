import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import ru.otus.sc.greet.dao.GreetingDaoImpl
import ru.otus.sc.greet.router.GreetRouter
import ru.otus.sc.greet.service.GreetingServiceImpl
import ru.otus.sc.user.dao.UserDaoImpl
import ru.otus.sc.user.router.UserRouter
import ru.otus.sc.user.service.UserServiceImpl
import akka.http.scaladsl.server.Directives._

import scala.io.StdIn

object Main {
  def main(args: Array[String]): Unit = {
    implicit val system: ActorSystem = ActorSystem("system")

    import system.dispatcher

    val userDao     = new UserDaoImpl
    val userService = new UserServiceImpl(userDao)

    val greetDao     = new GreetingDaoImpl
    val greetService = new GreetingServiceImpl(greetDao)

    val userRouter  = new UserRouter(userService)
    val greetRouter = new GreetRouter(greetService)

    val binding = Http().newServerAt("localhost", 8080).bind(userRouter.route ~ greetRouter.route)

    binding.foreach(b => println(s"Binding on ${b.localAddress}"))

    StdIn.readLine()

    binding.flatMap(_.unbind()).onComplete(_ => system.terminate())
  }
}
