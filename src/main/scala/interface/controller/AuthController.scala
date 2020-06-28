package interface.controller

import cats.effect.IO
import domain.entity.auth.{LoginReq, LoginResp}
import domain.entity.user.UserAttributes.{Password, UserId}
import usecase.user.{MixInUserUsecase, UsesUserUsecase}

trait AuthController extends UsesUserUsecase {
  def login(req: LoginReq): IO[LoginResp] = {
    val userId = UserId(req.userId)
    val pass = Password(req.password)

    userUsecase
      .login(userId, pass)
      .map(v => LoginResp(v.value))
  }
}

object AuthController extends AuthController with MixInUserUsecase

trait MixInAuthController {
  val authController: AuthController = AuthController
}
