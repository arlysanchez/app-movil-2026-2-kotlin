package pe.edu.upeu.di

import org.koin.dsl.module
import pe.edu.upeu.domain.repository.RoomRepository
import pe.edu.upeu.presentation.viewmodel.HomeViewModel

val appModule = module {
    //inyectamos el repository como una sola instancia
    single<RoomRepository> { RoomRepositoryImpl() }

    factory { HomeViewModel(get ()) }

}