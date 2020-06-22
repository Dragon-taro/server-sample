package interface.repository.user

private case class DbUser(id: Int,
                          name: String,
                          email: String,
                          userId: String,
                          password: String,
                          role: Int)
