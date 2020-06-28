package interface.repository.user

import cats.data.OptionT
import cats.effect.IO
import doobie.implicits._
import domain.entity.user.User
import domain.entity.user.UserAttributes.{
  EmailAddress,
  Id,
  Name,
  Password,
  Role,
  UserId
}
import domain.repository.user.UserRepository
import infra.datastore.sql.{MixInSql, UsesSql}

trait UserRepositoryImpl extends UserRepository with UsesSql {
  def find(id: Id): OptionT[IO, User] = {
    val q = sql"SELECT * from users where id = ${id.value}"
    OptionT(
      q.query[DbUser]
        .option
        .transact(sql.xa)
    ).map(dbUserToEntityUser)
  }

  def findById(userId: UserId): OptionT[IO, User] = {
    val q = sql"SELECT * from users where user_id = ${userId.value}"
    OptionT(
      q.query[DbUser]
        .option
        .transact(sql.xa)
    ).map(dbUserToEntityUser)
  }

  private def dbUserToEntityUser(dbUser: DbUser): User = {
    User(
      Id(dbUser.id),
      Name(dbUser.name),
      EmailAddress(dbUser.email_address),
      UserId(dbUser.user_id),
      Password(dbUser.password),
      Role(dbUser.role)
    )
  }
}

object UserRepositoryImpl extends UserRepositoryImpl with MixInSql

trait UsesUserRepository {
  val userRepository: UserRepository
}

trait MixInUserRepository {
  val userRepository: UserRepository = UserRepositoryImpl
}
