
import io.circe.Decoder
import io.circe.generic.semiauto._

package object config {

  implicit val serverDecoder: Decoder[ServerConfig] = deriveDecoder
  implicit val petstoreConfig: Decoder[PetStoreConfig] = deriveDecoder

}
