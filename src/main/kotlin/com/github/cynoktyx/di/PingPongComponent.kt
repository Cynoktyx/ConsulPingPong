package com.github.cynoktyx.di

import dagger.Component
import javax.inject.Singleton

/**
 * Created by lukas on 05.01.17
 */
@Singleton
@Component(modules = arrayOf(ApplicationModule::class, NetworkingModule::class))
interface PingPongComponent {

}