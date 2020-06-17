package domain.repository.user

import com.twitter.util.Future
import domain.entity.user.User
import domain.entity.user.UserAttributes.{Id, UserId}

trait UserRepository {
  def Find(id: Id): Future[Option[User]]
  def FindById(userId: UserId): Future[Option[User]]
}
