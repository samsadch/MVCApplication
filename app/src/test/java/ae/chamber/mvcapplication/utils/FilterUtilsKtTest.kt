package ae.chamber.mvcapplication.utils

import org.junit.Assert.*
import org.junit.Test

class FilterUtilsKtTest{
    @Test
    fun getFilteredDat_textNull_result_null(){
        //GIVEN
        val ressult = getFilteredData( null, null)
    }
}