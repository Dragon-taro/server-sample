package domain.repository.auth

import cats.effect.IO
import domain.entity.auth.SessionId
import domain.entity.user.UserAttributes.UserId

trait AuthRepository {
  def getSession(sessId: SessionId): IO[Option[UserId]]
  def setSession(userId: UserId): IO[SessionId]
}
