package pe.edu.upeu.di

import org.koin.dsl.module
import pe.edu.upeu.data.remote.api.ApiClient
import pe.edu.upeu.data.repository.RoomRepositoryImpl
import pe.edu.upeu.domain.repository.RoomRepository
import pe.edu.upeu.domain.usecase.GetRoomTypesUseCase
import pe.edu.upeu.presentation.viewmodel.HomeViewModel

val appModule = module {
    //API Cliente
    single { ApiClient.httpClient }

    //Repositories
    single<RoomRepository> { RoomRepositoryImpl(get()) }
    //useCase
    factory { GetRoomTypesUseCase(get()) }
    //viewModels
    factory { HomeViewModel(get ()) }

}