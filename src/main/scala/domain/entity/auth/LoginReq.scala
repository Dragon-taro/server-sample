package domain.entity.auth

case class LoginReq(userId: String, password: String)

case class LoginResp(session: String)
