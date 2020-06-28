package domain.entity.user

object UserAttributes {
  case class Id(value: Int)
  case class Name(value: String)
  case class EmailAddress(value: String)
  case class UserId(value: String)
  case class Password(value: String)

  sealed abstract class Role(value: Int)
  object Role {
    case object Admin extends Role(0)
    case object Member extends Role(1)

    def apply(value: Int): Role = value match {
      case 0 => Admin
      case 1 => Member
    }
  }
}
