package utils

import cats.effect.IO
import com.twitter.util.{Future, Return, Throw}

private[utils] object IOFromTwitterFuture {
  def apply[A](f: Future[A]): IO[A] = f.poll match {
    case Some(r) =>
      r match {
        case Return(r) => IO.pure(r)
        case Throw(e)  => IO.raiseError(e)
      }
    case _ =>
      IO.async { cb =>
        f.respond(
          r =>
            cb(r match {
              case Return(r) => Right(r)
              case Throw(e)  => Left(e)
            })
        )
      }
  }
}

object IO2 {
  def fromTwitterFuture[A](tf: Future[A]): IO[A] =
    IO(tf).flatMap(f => IOFromTwitterFuture(f))
}
