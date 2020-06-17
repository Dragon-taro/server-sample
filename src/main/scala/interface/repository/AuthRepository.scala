package interface.repository

import java.util.Date

import com.twitter.util.Future
import domain.entity.auth.SessionId
import domain.entity.user.UserAttributes.UserId
import domain.repository.auth.AuthRepository
import infra.datastore.redis.{UsesRedis, MixInRedis}

trait AuthRepositoryImpl extends AuthRepository with UsesRedis {
  def GetSession(sessId: SessionId): Future[Option[UserId]] =
    redis.get(sessId.value).map(_.map(UserId)) // voのデコード・エンコードを実装した方がきれいではありそう

  def SetSession(userId: UserId): Future[SessionId] = {
    val sessId = createSessionId(userId)
    redis.set(sessId.value, userId.value).map(_ => sessId)
  }

  private def createSessionId(userId: UserId): SessionId = {
    val now = new Date
    val sessId = userId.value + now.toString
    SessionId(sessId)
  }
}

object AuthRepositoryImpl extends AuthRepositoryImpl with MixInRedis

trait UsesAuthRepository {
  val authRepository: AuthRepository
}

trait MixInAuthRepository {
  val authRepository: AuthRepository = AuthRepositoryImpl
}
