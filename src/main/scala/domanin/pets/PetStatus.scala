package domanin.pets

import enumeratum._

sealed trait PetStatus extends EnumEntry

case object PetStatus extends Enum[PetStatus] with CirceEnum[PetStatus]{
  case object Available extends PetStatus
  case object Pending extends PetStatus
  case object Adopted extends PetStatus

  override def values: IndexedSeq[PetStatus] = findValues
}