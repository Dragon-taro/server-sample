package domain.repository.auth

import com.twitter.util.Future
import domain.entity.auth.SessionId
import domain.entity.user.UserAttributes.UserId

trait AuthRepository {
  def getSession(sessId: SessionId): Future[Option[UserId]]
  def setSession(userId: UserId): Future[SessionId]
}
