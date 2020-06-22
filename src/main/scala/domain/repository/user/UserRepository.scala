package domain.repository.user

import cats.data.OptionT
import cats.effect.IO
import domain.entity.user.User
import domain.entity.user.UserAttributes.{Id, UserId}

trait UserRepository {
  def find(id: Id): OptionT[IO, User]
  def findById(userId: UserId): OptionT[IO, User]
}
