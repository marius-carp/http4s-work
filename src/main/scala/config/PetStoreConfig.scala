package config

final case class ServerConfig(host: String, port: Int)
final case class PetStoreConfig(server: ServerConfig, db: DatabaseConfig)
