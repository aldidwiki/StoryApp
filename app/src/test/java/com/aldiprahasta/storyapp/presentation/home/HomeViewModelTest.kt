package com.aldiprahasta.storyapp.presentation.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.recyclerview.widget.ListUpdateCallback
import app.cash.turbine.test
import com.aldiprahasta.storyapp.DataDummy
import com.aldiprahasta.storyapp.MainDispatcherRule
import com.aldiprahasta.storyapp.data.source.local.entity.StoryEntity
import com.aldiprahasta.storyapp.domain.StoryRepository
import com.aldiprahasta.storyapp.domain.usecase.GetStories
import com.aldiprahasta.storyapp.domain.usecase.GetStoriesWithPaging
import com.aldiprahasta.storyapp.domain.usecase.wrapper.GetStoryWrapper
import com.aldiprahasta.storyapp.utils.mapToDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRules = MainDispatcherRule()

    @Mock
    private lateinit var storyRepository: StoryRepository

    @Test
    fun `when Get Story Should Not Null and Return Data`() = runTest {
        val dummyStory = DataDummy.generateDummyStories()
        val expectedStory = flowOf(StoryPagingSource.snapshot(dummyStory))
        Mockito.`when`(storyRepository.getStoriesWithPaging()).thenReturn(expectedStory)

        val homeViewModel = HomeViewModel(GetStoryWrapper(GetStories(storyRepository), GetStoriesWithPaging(storyRepository)))
        homeViewModel.storiesWithPaging.test {
            val actualStory = awaitItem()

            val differ = AsyncPagingDataDiffer(
                    diffCallback = StoryAdapter.DIFF_UTIL,
                    updateCallback = noopListUpdateCallback,
                    workerDispatcher = Dispatchers.Main,
            )
            differ.submitData(actualStory)

            val dummyStoryDomainModel = dummyStory.map { it.mapToDomainModel() }
            assertNotNull(differ.snapshot())
            assertEquals(dummyStoryDomainModel.size, differ.snapshot().size)
            assertEquals(dummyStoryDomainModel[0], differ.snapshot()[0])
        }

        Mockito.verify(storyRepository).getStoriesWithPaging()
    }

    @Test
    fun `when Get Story Empty Should Return No Data`() = runTest {
        val expectedStory = flowOf(PagingData.from(emptyList<StoryEntity>()))
        Mockito.`when`(storyRepository.getStoriesWithPaging()).thenReturn(expectedStory)

        val homeViewModel = HomeViewModel(GetStoryWrapper(GetStories(storyRepository), GetStoriesWithPaging(storyRepository)))
        homeViewModel.storiesWithPaging.test {
            val actualStory = awaitItem()

            val differ = AsyncPagingDataDiffer(
                    diffCallback = StoryAdapter.DIFF_UTIL,
                    updateCallback = noopListUpdateCallback,
                    workerDispatcher = Dispatchers.Main,
            )
            differ.submitData(actualStory)

            assertEquals(0, differ.snapshot().size)
        }

        Mockito.verify(storyRepository).getStoriesWithPaging()
    }
}


val noopListUpdateCallback = object : ListUpdateCallback {
    override fun onInserted(position: Int, count: Int) {}
    override fun onRemoved(position: Int, count: Int) {}
    override fun onMoved(fromPosition: Int, toPosition: Int) {}
    override fun onChanged(position: Int, count: Int, payload: Any?) {}
}

class StoryPagingSource : PagingSource<Int, Flow<List<StoryEntity>>>() {
    companion object {
        fun snapshot(items: List<StoryEntity>): PagingData<StoryEntity> {
            return PagingData.from(items)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Flow<List<StoryEntity>>>): Int {
        return 0
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Flow<List<StoryEntity>>> {
        return LoadResult.Page(emptyList(), 0, 1)
    }
}

