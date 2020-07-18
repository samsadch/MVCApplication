package ae.chamber.mvcapplication.utils

import ae.chamber.mvcapplication.model.Result

internal fun getFilteredData(
    text: String?,
    articleList: ArrayList<Result>?
): ArrayList<Result> {
    val temp = ArrayList<Result>()
    if(articleList!=null) {
        for (item in articleList) {
            if (item.title.toLowerCase().contains(text.toString().toLowerCase())) {
                temp.add(item)
            }
        }
    }
    return temp
}
