package com.iranmobiledev.moodino.utlis

import saman.zamani.persiandate.PersianDate
import java.util.*


class MoodinoDateImpl : MoodinoDate {

    override fun date(): PersianDate {
        return PersianDate()
    }
}


