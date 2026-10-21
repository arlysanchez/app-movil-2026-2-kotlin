package pe.edu.upeu.data.remote.mapper

import pe.edu.upeu.data.remote.dto.AuthResponse
import pe.edu.upeu.data.remote.dto.UserDto
import pe.edu.upeu.domain.model.User

fun AuthResponse.toDomain(): User{
    return User(
        id = this.user.id.toString(),
        name = this.user.name,
        lastname = this.user.lastname,
        email = this.user.email,
        phone = this.user.phone,
        token = this.token,
        image = this.user.image

    )
}
fun UserDto.toDomain(): User{
    return User(
        id = this.id.toString(),
        name = this.name,
        lastname = this.lastname,
        email =this.email,
        phone = this.phone,
        image = this.image
    )
}