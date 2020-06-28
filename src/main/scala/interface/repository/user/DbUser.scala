package interface.repository.user

private case class DbUser(id: Int,
                          name: String,
                          email_address: String,
                          user_id: String,
                          password: String,
                          role: Int)
