package infra.datastore.redis

import com.twitter.finagle.redis.Client
import com.twitter.finagle.Redis.newRichClient

trait RedisClient {
  val client: Client = newRichClient("localhost:16379")
}

object RedisClient extends RedisClient

trait UsesRedisClient {
  val redisClient: RedisClient
}

trait MixInRedisClient {
  val redisClient: RedisClient = RedisClient
}
