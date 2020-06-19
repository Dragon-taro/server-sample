package domain.entity.auth

import io.circe.generic.JsonCodec

@JsonCodec case class LoginReq(userId: String, password: String)
