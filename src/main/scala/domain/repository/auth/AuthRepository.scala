package domain.repository.auth

import cats.data.OptionT
import cats.effect.IO
import domain.entity.auth.SessionId
import domain.entity.user.UserAttributes.UserId

trait AuthRepository {
  def getSession(sessId: SessionId): OptionT[IO, UserId]
  def setSession(userId: UserId): IO[SessionId]
}
