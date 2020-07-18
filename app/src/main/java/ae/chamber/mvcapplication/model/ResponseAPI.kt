package ae.chamber.mvcapplication.model

data class ResponseAPI(
    val copyright: String,
    val num_results: Int,
    val results: List<Result>,
    val status: String
)

