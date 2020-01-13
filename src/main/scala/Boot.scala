import cats.effect.{ConcurrentEffect, ContextShift, ExitCode, IO, IOApp, Resource, Timer}
import org.http4s.server.{Router, Server}
import cats.implicits._
import config.PetStoreConfig
import org.http4s.implicits._
import domanin.helloworld.HelloWorld
import org.http4s.server.blaze.BlazeServerBuilder
import io.circe.config.parser

object Boot extends IOApp {

  def createServer[F[_]: ContextShift: ConcurrentEffect: Timer]: Resource[F, Server[F]] = {
    for {
      config <- Resource.liftF(parser.decodePathF[F, PetStoreConfig]("petstore"))
      httpApp = Router(
        "/hello" -> HelloWorld.endpoints[F]()
      ).orNotFound
      server <- BlazeServerBuilder[F]
        .bindHttp(config.server.port, config.server.host)
        .withHttpApp(httpApp)
        .resource
    } yield server
  }

  override def run(args: List[String]): IO[ExitCode] =
    createServer.use(_ => IO.never).as(ExitCode.Success)

}
