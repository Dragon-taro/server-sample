package interface.repository.auth

import cats.effect.IO
import com.twitter.util.Future
import domain.entity.auth.SessionId
import domain.entity.user.UserAttributes.UserId
import domain.repository.auth.AuthRepository
import infra.datastore.redis.{MixInRedis, UsesRedis}
import utils.{MixInDate, UsesDate}

trait AuthRepositoryImpl extends AuthRepository with UsesRedis with UsesDate {
  def getSession(sessId: SessionId): IO[Option[UserId]] =
    redis.get(sessId.value).map(_.map(UserId)) // voのデコード・エンコードを抽象化した方がきれいではありそう

  def setSession(userId: UserId): IO[SessionId] = {
    val sessId = createSessionId(userId)
    redis.set(sessId.value, userId.value).map(_ => sessId)
  }

  private def createSessionId(userId: UserId): SessionId = {
    val sessId = userId.value + date.nowToString
    SessionId(sessId)
  }
}

object AuthRepositoryImpl
    extends AuthRepositoryImpl
    with MixInRedis
    with MixInDate

trait UsesAuthRepository {
  val authRepository: AuthRepository
}

trait MixInAuthRepository {
  val authRepository: AuthRepository = AuthRepositoryImpl
}
