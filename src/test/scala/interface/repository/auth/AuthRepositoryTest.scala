package interface.repository.auth

import com.twitter.util.{Await, Future}
import domain.entity.auth.SessionId
import domain.entity.user.UserAttributes.UserId
import infra.datastore.redis.Redis
import utils.{DTDate, DTTest}

class AuthRepositoryTest extends DTTest {
  val mockDate = mock[DTDate]
  val mockRedis = mock[Redis]

  val subject = new AuthRepositoryImpl {
    override val date: DTDate = mockDate
    override val redis: Redis = mockRedis
  }

  val defaultSessId = SessionId("key")
  val defaultUserId = UserId("DragonTaro")

  describe("getSession") {
    it("works") {
      (mockRedis.get _).expects("key").returning(Future(Some("DragonTaro")))

      val expects = Some(defaultUserId)
      val actual = Await.result(subject.getSession(defaultSessId))

      actual shouldBe expects
    }
  }

  describe("setSession") {
    it("works") {
      val expectedSessId = "DragonTaro20200618"
      (mockDate.nowToString _).expects().returning("20200618")
      (mockRedis.set _)
        .expects(expectedSessId, defaultUserId.value)
        .returning(Future(SessionId(expectedSessId)))

      val expected = SessionId(expectedSessId)
      val actual = Await.result(subject.setSession(defaultUserId))

      actual shouldBe expected
    }
  }
}
