package domain.repository.user

import cats.effect.IO
import domain.entity.user.User
import domain.entity.user.UserAttributes.{Id, UserId}

trait UserRepository {
  def find(id: Id): IO[Option[User]]
  def findById(userId: UserId): IO[Option[User]]
}
