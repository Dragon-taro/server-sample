package domain.entity.user

import java.sql.Date

import domain.entity.user.UserAttributes.{
  EmailAddress,
  Id,
  Name,
  Password,
  Role,
  UserId
}

case class User(id: Id,
                name: Name,
                email: EmailAddress,
                userId: UserId,
                password: Password,
                role: Role)
