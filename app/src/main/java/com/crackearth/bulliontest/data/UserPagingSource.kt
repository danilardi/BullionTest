package com.crackearth.bulliontest.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.crackearth.bulliontest.data.remote.ApiService
import com.crackearth.bulliontest.model.DataItemUsersResponse

class UserPagingSource(private val apiService: ApiService, private val token: String? = null) :
    PagingSource<Int, DataItemUsersResponse>() {
    override fun getRefreshKey(state: PagingState<Int, DataItemUsersResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DataItemUsersResponse> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val responseData = apiService.getListUser(token).data
            LoadResult.Page(
                data = responseData,
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = if (responseData.isEmpty()) null else position + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
        const val TAG = "UserPagingSource"
    }
}