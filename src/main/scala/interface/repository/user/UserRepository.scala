package interface.repository.user

import cats.effect.IO
import com.twitter.finagle.mysql._
import com.twitter.util.Future
import doobie._
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
  def find(id: Id): IO[Option[User]] = {
    val q = sql"SELECT * from users where id = ?"
    q.query[DbUser].option.transact(sql.xa)
  }

  def findById(userId: UserId): Future[Option[User]] = {
    val query = "SELECT * from users where id = ?"
    val ps = sql.client.prepare(query)
    val result = ps.select(userId.value)(decodeUser)

    // というか共通化したい
    result.map {
      case Nil   => None
      case users => users.head
    }
  }

  private def decodeUser(row: Row): Option[User] =
    for {
      id <- row.getInteger("id")
      name <- row.getString("name")
      userId <- row.getString("user_id")
      email <- row.getString("email")
      pass <- row.getString("password")
      role <- row.getInteger("role")
      createdAt <- row.getJavaSqlDate("crated_at")
      updatedAt <- row.getJavaSqlDate("updated_at")
    } yield
      User(
        Id(id),
        Name(name),
        EmailAddress(email),
        UserId(userId),
        Password(pass),
        Role(role),
        createdAt,
        updatedAt
      )
}

object UserRepositoryImpl extends UserRepositoryImpl with MixInSql

trait UsesUserRepository {
  val userRepository: UserRepository
}

trait MixInUserRepository {
  val userRepository: UserRepository = UserRepositoryImpl
}
