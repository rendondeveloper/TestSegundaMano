package com.example.testsegundamano

import android.content.Context
import android.graphics.drawable.Drawable
import com.example.testsegundamano.coin.coin.dto.Coin
import com.example.testsegundamano.coin.coin.presenter.CoinPresenter
import org.junit.Before
import org.junit.Test
import com.example.testsegundamano.coin.coin.dto.api.DataCoin
import org.mockito.Mockito.*

class TestCode {

    lateinit var presenter: CoinPresenter

    lateinit var model: Coin.Model
    lateinit var view: Coin.View
    lateinit var context: Context
    lateinit var drawable: Drawable
    val text: String = "texto"

    @Before fun init(){
        //mocks
        context = mock(Context::class.java)
        model = mock(Coin.Model::class.java)
        view = mock(Coin.View::class.java)
        drawable = mock(Drawable::class.java)

        presenter = CoinPresenter(model, context);
        presenter.setView(view)

        `when`(context.getString(anyInt())).thenReturn(text)
        `when`(context.getDrawable(anyInt())).thenReturn(drawable)
    }

    /**
     * Se valida que se reciban todos los datos que se capturan en le view para consultar el historial de las divisas
     */
    @Test fun getSuccessDataGetHistory(){
        `when`(view.getFromCoin()).thenReturn("USD")
        `when`(view.getToCoin()).thenReturn("EUR")
        `when`(view.getLimit()).thenReturn("5")

        presenter.getHistory()

        verify(view, times(0)).showError("")
    }

    /**
     * Se valida el flujo donde solo se envían los datos de divisas y no el limite de días
     */
    @Test fun getErrorMissingDataGetHistory(){
        `when`(view.getFromCoin()).thenReturn("USD")
        `when`(view.getToCoin()).thenReturn("EUR")
        `when`(view.getLimit()).thenReturn("")
        `when`(context.getString(anyInt())).thenReturn(text)

         presenter.getHistory()

         verify(view, times(1)).showError(text)
    }

    /**
     * Se valida que al recibir una lista vacía se envíe el estado de emty state al usuario
     */
    @Test fun successGetEmptyDataHistory(){
        val listFake = listOf<DataCoin>()
        presenter.setHistoryList(listFake)
        verify(view, times(1)).showEmptyStateByCoinOptionList(text,text, drawable)
        verify(view, times(0)).setHistoryList(listFake)
    }

    /**
     * Se valida que al recibir una lista esta sea enviada a la vista del usuario
     */
    @Test fun successGetDataHistory(){
        val listFake = listOf<DataCoin>().plus(any<DataCoin>())
        presenter.setHistoryList(listFake)
        verify(view, times(0)).showEmptyStateByCoinOptionList(text,text, drawable)
        verify(view, times(1)).setHistoryList(listFake)
    }

    /**
     * Se valida que al recibir una lista esta sea enviada a la vista del usuario
     */
    @Test fun successGetDataCoinOptions(){
        val listFake = listOf<String>().plus(text)
        presenter.setCoinOption(listFake)
        verify(view, times(0)).showEmptyStateByCoinOptionList(text,text, drawable)
        verify(view, times(1)).showReadyState(text,text, drawable)
        verify(view, times(1)).setCoinOptionList(listFake)
    }

    /**
     * Se valida que al recibir una lista vacía se envíe el estado de emty state al usuario
     */
    @Test fun successGetZeroDataCoinOptions(){
        presenter.setCoinOption(anyList())
        verify(view, times(1)).showEmptyStateByCoinOptionList(text,text, drawable)
    }
}