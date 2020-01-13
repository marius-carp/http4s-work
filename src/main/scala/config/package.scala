
import io.circe.Decoder
import io.circe.generic.semiauto._

package object config {

  implicit val serverDecoder: Decoder[ServerConfig] = deriveDecoder
  implicit val databaseConnectionConfigDecoder: Decoder[DatabaseConnectionConfig] = deriveDecoder
  implicit val databaseDecoder: Decoder[DatabaseConfig] = deriveDecoder
  implicit val petstoreConfig: Decoder[PetStoreConfig] = deriveDecoder

}
