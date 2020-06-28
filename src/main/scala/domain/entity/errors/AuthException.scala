package domain.entity.errors

sealed abstract class AuthException(why: String) extends CustomException(why)

case object UnauthorizedException extends AuthException("認証されていません。")
case object InvalidUserIdOrPasswordException
    extends AuthException("UserIdまたはパスワードが正しくありません。")
