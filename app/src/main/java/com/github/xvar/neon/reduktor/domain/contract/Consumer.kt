package com.github.xvar.neon.reduktor.domain.contract

//Данный интерфейс не нужен
//Завёл на раннем этапе, когда искал, что передавать в compose-функцию экрана:
//ViewModel, Observable, Consumer event'ов, набор
interface Consumer<in T> {

    fun consume(event: T) {}

    object Empty: Consumer<Any>

}