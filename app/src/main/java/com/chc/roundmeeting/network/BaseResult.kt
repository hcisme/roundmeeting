package com.chc.roundmeeting.network

/**
 * 网络请求第一层返回的结果
 */
data class BaseResult<T>(
    val status: String,
    val code: Int,
    val info: String,
    val data: T?
)

/**
 * 分页
 */
//data class PaginationData<T> (
//    val totalCount: Long,
//    val pageSize: Long,
//    val page: Long,
//    val pageTotal: Long,
//    val list: List<T>
//)
