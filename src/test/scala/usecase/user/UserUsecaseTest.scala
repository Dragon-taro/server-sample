//package usecase.user
//
//import java.sql.Date
//
//import com.twitter.util.{Await, Future}
//import domain.entity.auth.SessionId
//import domain.entity.errors.{
//  InvalidUserIdOrPasswordException,
//  UnauthorizedException
//}
//import domain.entity.user.User
//import domain.entity.user.UserAttributes.Role.Admin
//import domain.entity.user.UserAttributes.{
//  EmailAddress,
//  Id,
//  Name,
//  Password,
//  UserId
//}
//import domain.repository.auth.AuthRepository
//import domain.repository.user.UserRepository
//import utils.DTTest
//
//class UserUsecaseTest extends DTTest {
//
//  val mockAuthRepository = mock[AuthRepository]
//  val mockUserRepository = mock[UserRepository]
//
//  val subject = new UserUsecase {
//    override val authRepository: AuthRepository = mockAuthRepository
//    override val userRepository: UserRepository = mockUserRepository
//  }
//
//  val now = new Date(2020, 6, 18)
//  val defaultUserId = UserId("DragonTaro")
//  val defaultPassword = Password("pass")
//  val defaultUser = User(
//    id = Id(1),
//    name = Name("宮川竜太朗"),
//    email = EmailAddress("mock@mail.com"),
//    userId = defaultUserId,
//    password = defaultPassword,
//    role = Admin,
//    createdAt = now,
//    updatedAt = now
//  )
//
////  // mysqlの接続がうまくいかない
////  describe("動作確認") {
////    val subject = new UserUsecase {
////      override val authRepository: AuthRepository = AuthRepositoryImpl
////      override val userRepository: UserRepository = UserRepositoryImpl
////    }
////
////    it("works") {
////      Await.result(subject.login(UserId("DragonTaro"), Password("hogehoge")))
////    }
////  }
//
//  describe("login") {
//    describe("指定されたuserIdのuserが存在しないとき") {
//      it("InvalidUserIdOrPasswordExceptionを返すこと") {
//        (mockUserRepository.FindById _)
//          .expects(defaultUserId)
//          .once()
//          .returning(Future(None))
//
//        val expects = InvalidUserIdOrPasswordException.getMessage
//
//        val result = subject.login(defaultUserId, defaultPassword) map (_.value) handle {
//          case e: Exception => e.getMessage
//        }
//        val actual = Await.result(result)
//
//        actual shouldBe expects
//      }
//    }
//
//    describe("指定されたパスワードとDB上のパスワードが一致しないとき") {
//      it("InvalidUserIdOrPasswordExceptionを返すこと") {
//        (mockUserRepository.FindById _)
//          .expects(defaultUserId)
//          .once()
//          .returning(Future(Some(defaultUser)))
//
//        val expects = InvalidUserIdOrPasswordException.getMessage
//
//        val invalidPassword = Password("fugafuga")
//        val result = subject.login(defaultUserId, invalidPassword) map (_.value) handle {
//          case e: Exception => e.getMessage
//        }
//        val actual = Await.result(result)
//
//        actual shouldBe expects
//      }
//    }
//
//    describe("Userが存在しパスワードも正しいとき") {
//      it("redisへの保存が実行されsessionIdが返されること") {
//        val expects = SessionId("expectedSessionId")
//
//        (mockUserRepository.FindById _)
//          .expects(defaultUserId)
//          .once()
//          .returning(Future(Some(defaultUser)))
//        (mockAuthRepository.setSession _)
//          .expects(defaultUserId)
//          .returning(Future(expects))
//
//        val actual = Await.result(subject.login(defaultUserId, defaultPassword))
//
//        actual shouldBe expects
//      }
//    }
//  }
//
//  describe("auth") {
//    describe("sessionIdがないとき") {
//      it("UnauthorizedExceptionを返すこと") {
//        val sessId = SessionId("mockSessionId")
//        (mockAuthRepository.getSession _)
//          .expects(sessId)
//          .returning(Future(None))
//
//        val expects = UnauthorizedException.getMessage
//
//        val result = subject.auth(sessId) map (_.value) handle {
//          case e: Exception => e.getMessage
//        }
//        val actual = Await.result(result)
//
//        actual shouldBe expects
//      }
//    }
//
//    describe("sessionIdがあるとき") {
//      it("UserIdを返すこと") {
//        val sessId = SessionId("mockSessionId")
//        (mockAuthRepository.getSession _)
//          .expects(sessId)
//          .returning(Future(Some(defaultUserId)))
//
//        val expects = defaultUserId
//        val actual = Await.result(subject.auth(sessId))
//
//        actual shouldBe expects
//      }
//    }
//  }
//}
