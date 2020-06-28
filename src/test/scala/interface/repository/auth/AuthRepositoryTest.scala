package interface.repository.auth

import cats.data.OptionT
import cats.effect.IO
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
      (mockRedis.get _)
        .expects("key")
        .returning(OptionT[IO, String](IO.pure(Some("DragonTaro"))))

      val expects = Some(defaultUserId)
      val actual = subject.getSession(defaultSessId).value.unsafeRunSync

      actual shouldBe expects
    }
  }

  describe("setSession") {
    it("works") {
      val expectedSessId = "DragonTaro20200618"
      (mockDate.nowToString _).expects().returning("20200618")
      (mockRedis.set _)
        .expects(expectedSessId, defaultUserId.value)
        .returning(IO.unit)

      val expected = SessionId(expectedSessId)
      val actual = subject.setSession(defaultUserId).unsafeRunSync

      actual shouldBe expected
    }
  }
}
