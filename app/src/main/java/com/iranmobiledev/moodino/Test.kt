package com.iranmobiledev.moodino

import com.iranmobiledev.moodino.utlis.ImageLoadingService
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf

class Test : KoinComponent{
    private val imageLoader : ImageLoadingService by inject{ parametersOf("Mohammad", "Mahdi")}
}