package infra.datastore.redis

import com.twitter.util.Future
import com.twitter.io.Buf.Utf8

trait Redis extends UsesRedisClient {
  def get(key: String): Future[Option[String]] =
    redisClient.client.get(Utf8(key)).map(_.flatMap(Utf8.unapply))

  def set(key: String, value: String): Future[Unit] =
    redisClient.client.set(Utf8(key), Utf8(value))
}

object Redis extends Redis with MixInRedisClient

trait UsesRedis {
  val redis: Redis
}

trait MixInRedis {
  val redis: Redis = Redis
}
