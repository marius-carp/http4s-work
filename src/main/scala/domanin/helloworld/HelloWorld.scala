package domanin.helloworld

import cats.effect.Sync
import org.http4s.{EntityDecoder, HttpRoutes}
import org.http4s.dsl.Http4sDsl
import org.http4s.circe._
import io.circe.generic.auto._
import io.circe.syntax._

class HelloWorld[F[_]: Sync] extends Http4sDsl[F] {

  case class Greeting(greeting: String)

  implicit val petDecoder: EntityDecoder[F, Greeting] = jsonOf[F, Greeting]

  def endpoints(): HttpRoutes[F] = {
    HttpRoutes.of[F] {
      case GET -> Root / "world" =>
        Ok("Hello World")

      case GET -> Root / "world" / name =>
        Ok(Greeting(name).asJson)
    }
  }

}

object HelloWorld {

  def endpoints[F[_]: Sync](): HttpRoutes[F] = {
    new HelloWorld[F].endpoints()
  }

}
