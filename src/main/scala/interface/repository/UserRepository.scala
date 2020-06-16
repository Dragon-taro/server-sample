package interface.repository

import com.twitter.util.Future
import domain.entity.user.User
import domain.entity.user.UserAttributes.{Id, UserId}
import domain.repository.user.UserRepository

trait UserRepositoryImpl extends UserRepository {
  def Find(id: Id): Future[User] = ???
  def FindById(userId: UserId): Future[User] = ???
}

object UserRepositoryImpl extends UserRepositoryImpl

trait UsesUserRepository {
  val userRepository: UserRepository
}

object MixInUserRepository {
  val userRepository: UserRepository = UserRepositoryImpl
}
