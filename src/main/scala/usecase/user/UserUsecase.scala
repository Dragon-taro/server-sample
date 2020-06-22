package usecase.user

import cats.effect.IO
import domain.entity.auth.SessionId
import domain.entity.errors.{
  InvalidUserIdOrPasswordException,
  UnauthorizedException
}
import domain.entity.user.UserAttributes.{Password, UserId}
import interface.repository.auth.{MixInAuthRepository, UsesAuthRepository}
import interface.repository.user.{MixInUserRepository, UsesUserRepository}

trait UserUsecase extends UsesAuthRepository with UsesUserRepository {
  def login(userId: UserId, pass: Password): IO[SessionId] = {
    for {
      user <- userRepository
        .findById(userId)
        .getOrElseF(IO.raiseError(InvalidUserIdOrPasswordException))
        .flatMap(
          user =>
            if (user.password == pass) IO.pure(user)
            else IO.raiseError(InvalidUserIdOrPasswordException)
        )
      sessId <- authRepository.setSession(user.userId)
    } yield sessId
  }

  def auth(sessId: SessionId): IO[UserId] =
    authRepository
      .getSession(sessId)
      .getOrElseF(IO.raiseError(UnauthorizedException))
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
