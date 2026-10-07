package pe.edu.upeu.presentation

import pe.edu.upeu.domain.model.RoomType

sealed class Screen {

    object Splash : Screen()
    object Welcome: Screen()
    object Home: Screen()
    data class RoomDetail(val room: RoomType) : Screen()

    object Cart: Screen()

    object ProfileUpdate: Screen()
}