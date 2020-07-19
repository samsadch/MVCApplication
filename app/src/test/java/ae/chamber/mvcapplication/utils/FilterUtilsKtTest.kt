package ae.chamber.mvcapplication.utils

import ae.chamber.mvcapplication.model.Result
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.*
import org.junit.Test

class FilterUtilsKtTest{
    @Test
    fun getFilteredDat_textNull_result_null(){
        val text:String? = null
        val list : ArrayList<Result>? = null

        val result = getFilteredData( text, list)

        assertThat(result, `is`(emptyList<Result>()))
    }

    @Test
    fun getFilteredDat_textEmpty_result_null(){
        val text:String? = ""
        val list : ArrayList<Result>? = null

        val result = getFilteredData( text, list)

        assertThat(result, `is`(emptyList<Result>()))
    }

    @Test
    fun getFilteredDat_textNotEmpty_result_null(){
        val text:String? = "testData"
        val list : ArrayList<Result>? = null

        val result = getFilteredData( text, list)

        assertThat(result, `is`(emptyList<Result>()))
    }
}