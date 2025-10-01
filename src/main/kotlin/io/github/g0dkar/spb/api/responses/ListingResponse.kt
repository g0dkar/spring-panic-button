package io.github.g0dkar.spb.api.responses

data class ListingResponse<T>(
    val page: Int,
    val pageSize: Int,
    val items: List<T>,
    val totalItemsCount: Long,
    val offset: Int = page * pageSize,
    val totalPages: Long = totalItemsCount / pageSize,
    val pageItemCount: Int = items.size,
    val orderByField: String? = null,
)

fun <T> List<T>.toListingResponse(
    page: Int,
    pageSize: Int,
    totalItemsCount: Long,
    orderByField: String? = null,
): ListingResponse<T> =
    ListingResponse(
        page = page,
        pageSize = pageSize,
        items = this,
        totalItemsCount = totalItemsCount,
        orderByField = orderByField,
    )

fun <T, R> List<T>.mapToListingResponse(
    page: Int,
    pageSize: Int,
    totalItemsCount: Long,
    orderByField: String? = null,
    mapFunction: (T) -> R,
): ListingResponse<R> =
    map(mapFunction)
        .toListingResponse(page, pageSize, totalItemsCount, orderByField)
