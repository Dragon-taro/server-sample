package domain.entity.errorResp

case class ErrorResp(errors: Seq[String])

object ErrorResp {
  def fromException(e: Exception) = ErrorResp(Seq(e.getMessage))
}
