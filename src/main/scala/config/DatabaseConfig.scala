package config

import cats.effect.{Async, Blocker, ContextShift, Resource, Sync}
import doobie.hikari.HikariTransactor
import org.flywaydb.core.Flyway
import cats.syntax.functor._
import scala.concurrent.ExecutionContext


case class DatabaseConnectionConfig(poolSize: Int)
case class DatabaseConfig(url: String,
                          user: String,
                          driver: String,
                          password: String,
                          connections: DatabaseConnectionConfig)

object DatabaseConfig {

  def dbTransactor[F[_]: Async: ContextShift](dbc: DatabaseConfig,
                                              connectionEc: ExecutionContext,
                                              blocker: Blocker): Resource[F, HikariTransactor[F]] =
    HikariTransactor.newHikariTransactor[F](dbc.driver, dbc. url, dbc.user, dbc.password, connectionEc, blocker)

  def initializeDb[F[_]](dbc: DatabaseConfig)(implicit S: Sync[F]): F[Unit] =
    S.delay {
      val fw: Flyway = {
        Flyway.configure()
          .dataSource(dbc.url, dbc.user, dbc.password)
          .load()
      }
      fw.migrate()
    }
    .as(())

}


