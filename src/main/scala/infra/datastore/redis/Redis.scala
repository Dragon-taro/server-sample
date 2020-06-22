package infra.datastore.redis

import cats.effect.IO
import com.twitter.io.Buf.Utf8
import utils.IO2

trait Redis extends UsesRedisClient {
  def get(key: String): IO[Option[String]] =
    IO2.fromTwitterFuture(
      redisClient.client.get(Utf8(key)).map(_.flatMap(Utf8.unapply))
    )

  def set(key: String, value: String): IO[Unit] =
    IO2.fromTwitterFuture(redisClient.client.set(Utf8(key), Utf8(value)))
}

object Redis extends Redis with MixInRedisClient

trait UsesRedis {
  val redis: Redis
}

trait MixInRedis {
  val redis: Redis = Redis
}
