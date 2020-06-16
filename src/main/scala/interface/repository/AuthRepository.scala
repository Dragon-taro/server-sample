package interface.repository

import java.util.Date

import com.twitter.util.Future
import domain.entity.auth.SessionId
import domain.entity.user.UserAttributes.UserId
import domain.repository.auth.AuthRepository

trait AuthRepositoryImpl extends AuthRepository {
  def GetSession(sessId: SessionId): Future[UserId] = ???

  def SetSession(userId: UserId): Future[SessionId] = {
    Future(createSessionId(userId))
  }

  private def createSessionId(userId: UserId): SessionId = {
    val now = new Date
    val sessId = userId.value + now.toString
    SessionId(sessId)
  }
}

object AuthRepositoryImpl extends AuthRepositoryImpl

trait UsesAuthRepository {
  val authRepository: AuthRepository
}

object MixInAuthRepository {
  val authRepository: AuthRepository = AuthRepositoryImpl
}
