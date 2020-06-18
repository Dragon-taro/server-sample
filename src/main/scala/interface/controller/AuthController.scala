package interface.controller

import com.twitter.util.Await
import domain.entity.user.UserAttributes.{Password, UserId}
import usecase.user.UsesUserUsecase

trait AuthController extends UsesUserUsecase {
  def login(userId: UserId, password: Password): String = {
    val mockUserId = UserId("DragonTaro")
    val mockPassword = Password("hogehgoe")

    val futResult =
      userUsecase.login(mockUserId, mockPassword).map(_.value).handle {
        case e: Exception => e.getMessage
      }

    Await.result(futResult)
  }
}
