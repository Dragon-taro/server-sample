package usecase.user

import cats.data.OptionT
import cats.effect.IO
import domain.entity.auth.SessionId
import domain.entity.errors.{
  InvalidUserIdOrPasswordException,
  UnauthorizedException
}
import domain.entity.user.User
import domain.entity.user.UserAttributes.Role.Admin
import domain.entity.user.UserAttributes.{
  EmailAddress,
  Id,
  Name,
  Password,
  UserId
}
import domain.repository.auth.AuthRepository
import domain.repository.user.UserRepository
import utils.DTTest

class UserUsecaseTest extends DTTest {

  val mockAuthRepository = mock[AuthRepository]
  val mockUserRepository = mock[UserRepository]

  val subject = new UserUsecase {
    override val authRepository: AuthRepository = mockAuthRepository
    override val userRepository: UserRepository = mockUserRepository
  }

  val defaultUserId = UserId("DragonTaro")
  val defaultPassword = Password("pass")
  val defaultUser = User(
    id = Id(1),
    name = Name("宮川竜太朗"),
    email = EmailAddress("mock@mail.com"),
    userId = defaultUserId,
    password = defaultPassword,
    role = Admin
  )

  describe("login") {
    describe("指定されたuserIdのuserが存在しないとき") {
      it("InvalidUserIdOrPasswordExceptionを返すこと") {
        (mockUserRepository.findById _)
          .expects(defaultUserId)
          .once()
          .returning(OptionT[IO, User](IO.pure(None)))

        val expects = InvalidUserIdOrPasswordException

        val actual =
          subject
            .login(defaultUserId, defaultPassword)
            .handleErrorWith(IO.pure)
            .unsafeRunSync

        actual shouldBe expects
      }
    }

    describe("指定されたパスワードとDB上のパスワードが一致しないとき") {
      it("InvalidUserIdOrPasswordExceptionを返すこと") {
        (mockUserRepository.findById _)
          .expects(defaultUserId)
          .once()
          .returning(OptionT[IO, User](IO.pure(Some(defaultUser))))

        val expects = InvalidUserIdOrPasswordException

        val invalidPassword = Password("fugafuga")
        val actual =
          subject
            .login(defaultUserId, invalidPassword)
            .handleErrorWith(IO.pure)
            .unsafeRunSync

        actual shouldBe expects
      }
    }

    describe("Userが存在しパスワードも正しいとき") {
      it("redisへの保存が実行されsessionIdが返されること") {
        val expects = SessionId("expectedSessionId")

        (mockUserRepository.findById _)
          .expects(defaultUserId)
          .once()
          .returning(OptionT[IO, User](IO.pure(Some(defaultUser))))
        (mockAuthRepository.setSession _)
          .expects(defaultUserId)
          .returning(IO.pure(expects))

        val actual = subject
          .login(defaultUserId, defaultPassword)
          .unsafeRunSync

        actual shouldBe expects
      }
    }
  }

  describe("auth") {
    describe("sessionIdがないとき") {
      it("UnauthorizedExceptionを返すこと") {
        val sessId = SessionId("mockSessionId")
        (mockAuthRepository.getSession _)
          .expects(sessId)
          .returning(OptionT[IO, UserId](IO.pure(None)))

        val expects = UnauthorizedException

        val actual = subject.auth(sessId).handleErrorWith(IO.pure).unsafeRunSync

        actual shouldBe expects
      }
    }

    describe("sessionIdがあるとき") {
      it("UserIdを返すこと") {
        val sessId = SessionId("mockSessionId")
        (mockAuthRepository.getSession _)
          .expects(sessId)
          .returning(OptionT[IO, UserId](IO.pure(Some(defaultUserId))))

        val expects = defaultUserId
        val actual = subject.auth(sessId).unsafeRunSync

        actual shouldBe expects
      }
    }
  }
}
