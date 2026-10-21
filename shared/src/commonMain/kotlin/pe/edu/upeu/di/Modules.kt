package pe.edu.upeu.di

import org.koin.dsl.module
import pe.edu.upeu.data.local.SessionManager
import pe.edu.upeu.data.remote.api.ApiClient
import pe.edu.upeu.data.repository.AuthRepositoryImpl
import pe.edu.upeu.data.repository.RoomRepositoryImpl
import pe.edu.upeu.domain.repository.AuthRepository
import pe.edu.upeu.domain.repository.RoomRepository
import pe.edu.upeu.domain.usecase.GetRoomTypesUseCase
import pe.edu.upeu.domain.usecase.LoginUseCase
import pe.edu.upeu.domain.usecase.RegisterUseCase
import pe.edu.upeu.presentation.viewmodel.AuthViewModel
import pe.edu.upeu.presentation.viewmodel.HomeViewModel

val appModule = module {
    //creacion de sesion en el movil
    single { createSettings() }
    single { SessionManager(get()) }

    //API Cliente
    single { ApiClient.httpClient }

    //Repositories
    single<RoomRepository> { RoomRepositoryImpl(get()) }
    single<AuthRepository>{ AuthRepositoryImpl(get(),get()) }
    //useCase
    factory { GetRoomTypesUseCase(get()) }
    factory { RegisterUseCase(get()) }
    factory { LoginUseCase(get()) }
    //viewModels
    factory { HomeViewModel(get (),get()) }
    factory { AuthViewModel(get (),get(),get()) }


}