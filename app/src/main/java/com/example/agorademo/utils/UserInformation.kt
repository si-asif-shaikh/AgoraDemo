package com.example.agorademo.utils

enum class UserInformation(val userID: String,
                           val  userPassword: String,
                           val userToken: String ?= ""
) {
    USER1("user2411",
        "Sportz@123",
        "007eJxTYMiqf7zRNsPl94VYHu6DGhof3SPX8qa1CcXpxF5YFuJ3b48Cg5mBRYq5palxqmGamYlFmlmSgYmlcVKyaaKFgVGSeWpadFNnckMgI4PMHn1GRgZWBkYgBPFVGCyMTMzSUi0NdM2SkhN1DQ1TU3QTDQ0TdS3MTcwNTYyNEw0tjADOFiUh"),

    USER2("user911",
        "asif",
        "007eJxTYPhheT+J6ytDnu9Ov7ycL0VK3EXFE3t+NDQumdefLeX1yEiBwczAIsXc0tQ41TDNzMQizSzJwMTSOCnZNNHCwCjJPDXNpKkzuSGQkWGBVyMrIwMrAyMQgvgqDBbJqSlGqUYGumZJaWm6hoapKbqWRskmupZJRoaWZklAzaYGADpgJuU=")
}