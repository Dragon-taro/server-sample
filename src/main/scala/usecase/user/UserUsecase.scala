package usecase.user

import com.twitter.util.Future
import domain.entity.auth.SessionId
import domain.entity.errors.{
  InvalidUserIdOrPasswordException,
  UnauthorizedException
}
import domain.entity.user.UserAttributes.{Password, UserId}
import interface.repository.auth.{MixInAuthRepository, UsesAuthRepository}
import interface.repository.user.{MixInUserRepository, UsesUserRepository}

trait UserUsecase extends UsesAuthRepository with UsesUserRepository {
  def login(userId: UserId, pass: Password): Future[SessionId] = {
    for {
      user <- userRepository
        .FindById(userId)
        .flatMap {
          case Some(user) => Future(user)
          case None       => Future.exception(InvalidUserIdOrPasswordException)
        }
        .flatMap(
          user =>
            if (user.password == pass) Future(user)
            else Future.exception(InvalidUserIdOrPasswordException)
        )
      sessId <- authRepository.SetSession(user.userId)
    } yield sessId
  }

  def auth(sessId: SessionId): Future[UserId] =
    authRepository.GetSession(sessId).flatMap {
      case Some(value) => Future(value)
      case None        => Future.exception(UnauthorizedException)
    }
}

object UserUsecase
    extends UserUsecase
    with MixInAuthRepository
    with MixInUserRepository

trait UsesUserUsecase {
  val userUsecase: UserUsecase
}

trait MixInUserUsecase {
  val userUsecase: UserUsecase = UserUsecase
}
