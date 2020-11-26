package com.example.testsegundamano.coin.coin.model

import com.example.testsegundamano.coin.coin.dto.Coin
import com.example.testsegundamano.coin.coin.dto.CoinState
import com.example.testsegundamano.coin.coin.dto.api.DataCoin
import com.example.testsegundamano.coin.coin.dto.api.ResponseCatalogCoin
import com.example.testsegundamano.coin.coin.dto.api.ResponseCoin
import kotlinx.coroutines.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CoinModel
    constructor(private val repository: Coin.Repository)
    : Coin.Model {

    private lateinit var presenter: Coin.Presenter
    private val formatByServices = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    override fun setPresenter(presenter: Coin.Presenter) {
        this.presenter = presenter
    }

    /**
     * Se realiza la petición al repository y responde a través del presenter con la lista de monedas para la vista
     * [fromCoin] moneda de origen
     * [toCoin] moneda destino
     * [limit] es el total de días que se restan a la fecha actual obtenida del dispositivo
     */
    override fun getHistory(fromCoin: String, toCoin: String, limit: Int) {
        if(!repository.availableNetwork()){
            this.presenter.showErrorHistoryEmpty();
            return
        }

        val list : MutableList<DataCoin> = mutableListOf()
        GlobalScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO){
                for (counter in 0 until limit){
                    val item = launchRequestRepository(counter, fromCoin, toCoin)
                    if(item != null){
                        list.add(item)
                    }
                }
            }
            calculateStaleOverList(list)
            presenter.setHistoryList(list)
        }
    }

    /**
     * Lanza las peticiones al repository con los datos
     * [counter] para restar días a la fecha actual
     * [fromCoin] moneda de origen
     * [toCoin] moneda destino
     */
    private suspend fun launchRequestRepository(counter: Int, fromCoin: String, toCoin: String) : DataCoin?{
        val dateCurrent = LocalDateTime.now().minusDays(counter.toLong()).format(formatByServices).toString()
        val response = repository.getHistory(dateCurrent,"$fromCoin,$toCoin")
        return if(response != null){
            buildItemView(response, fromCoin, toCoin)
        }else{
            null
        }
    }

    /**
     * Realiza el recorrido de la lista para obtener los valores de las monedas en cada objeto
     * [list] se utiliza un parametro y se mofica por refencia los datos del arrowFrom y arrowTo
     */
    private fun calculateStaleOverList(list : MutableList<DataCoin>){
        for (counter in 0 until list.size){
            val itemFirst = list[counter]
            if(counter < list.size - 1) {
                val itemSecond = list[counter + 1]
                itemFirst.arrowFrom = this.calculateValue(itemFirst.valueFrom, itemSecond.valueFrom)
                itemFirst.arrowTo = this.calculateValue(itemFirst.valueTo, itemSecond.valueTo)
            }
        }
    }

    /**
     * Se realiza una validación de los datos para determinar el estado de la moneda basandonos en los estados
     * conocidos del enum CoinState.
     * [valueBefore] Valor de la moneda antes
     * [valueAfter] Valor de la moneda despues
     * @return El estado de la moneda para el objeto evaluado
     */
    private fun calculateValue(valueBefore: Double, valueAfter: Double) : CoinState{
        return when {
            valueAfter == valueBefore -> {
                CoinState.EQUAL
            }
            valueBefore < valueAfter -> {
                CoinState.DOWN
            }
            valueBefore > valueAfter -> {
                CoinState.UP
            }
            else -> {
                CoinState.NONE
            }
        }
    }

    /**
     * Contruye un objeto tipo DataCoin.
     * [response] La respuesta del repositorio
     * [fromCoin] moneda de origen
     * [toCoin] moneda destino
     */
    private fun buildItemView(response: ResponseCoin, fromCoin: String, toCoin: String) : DataCoin{
       return DataCoin(
            date = LocalDate.parse(response.date, DateTimeFormatter.ISO_DATE),
            coinFrom = fromCoin,
            coinTo = toCoin,
            valueFrom = (response.rates[fromCoin] ?: "0.0").toDouble(),
            valueTo =  (response.rates[toCoin] ?: "0.0").toDouble()
       )
    }

    /**
     * Se realiza la petición al repository para obtener los distintos tipos de monedas
     */
    override fun getCoinOption(){

        if(!repository.availableNetwork()){
            this.presenter.showErrorCoinOption(true);
            return
        }

        this.repository.getCoinOption( object : Coin.CallBackServices {
            override fun <T> onCompletedCall(response: T) {
                if (response is ResponseCatalogCoin) {
                    validAllTypeCoin(response);
                } else if (response is Throwable) {
                    presenter.showErrorCoinOption(false)
                }
            }
        }
        );
    }

    /**
     * Se valida la respuesta del servicio del catalogo de los países
     * [response] respuesta de la consulta de las monedas
     */
    private fun validAllTypeCoin(response : ResponseCatalogCoin){
        if(response.success){
            val list = buildListStringView(response.symbols)
            this.presenter.setCoinOption(list)
        }else{
            this.presenter.showErrorCoinOption(false);
        }
    }

    /**
     * Se recorre el map que trae las claves de paises y se agregan a un lista de Strings
     */
    private fun buildListStringView(symbols: Map<String, String>) : List<String>{
        val list : MutableList<String> = mutableListOf()
        for (symbol in symbols) {
            list.add(symbol.key)
        }
        return list
    }
}