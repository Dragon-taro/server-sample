package domain.repository.auth

import com.twitter.util.Future
import domain.entity.auth.SessionId
import domain.entity.user.UserAttributes.UserId

trait AuthRepository {
  def GetSession(sessId: SessionId): Future[UserId]
  def SetSession(userId: UserId): Future[SessionId]
}
