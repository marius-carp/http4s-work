import cats.effect.{ConcurrentEffect, ContextShift, ExitCode, IO, IOApp, Resource, Timer}
import org.http4s.server.{Router, Server}
import cats.implicits._
import org.http4s.implicits._
import domanin.helloworld.HelloWorld
import org.http4s.server.blaze.BlazeServerBuilder

object Boot extends IOApp {

  def createServer[F[_]: ContextShift: ConcurrentEffect: Timer]: Resource[F, Server[F]] = {
    for {
      _ <- Resource.pure[F, String]("")
      httpApp = Router(
        "/hello" -> HelloWorld.endpoints[F]()
      ).orNotFound
      server <- BlazeServerBuilder[F]
        .bindHttp(8080, "localhost")
        .withHttpApp(httpApp)
        .resource
    } yield server
  }

  override def run(args: List[String]): IO[ExitCode] =
    createServer.use(_ => IO.never).as(ExitCode.Success)

}
