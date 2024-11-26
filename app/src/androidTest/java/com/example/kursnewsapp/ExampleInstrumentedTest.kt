package com.example.kursnewsapp


import com.example.kursnewsapp.data.api.newsAPI
import com.example.kursnewsapp.domain.Article
import com.example.kursnewsapp.domain.Source
import com.example.kursnewsapp.presentation.util.Constants
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewsApiTest {

    private lateinit var Api: newsAPI


    @Before
    fun setUp() {
        val client = OkHttpClient.Builder().build()

        val ret = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        val api by lazy {
            ret.create(newsAPI::class.java)
        }
        Api = api

    }

    @Test
    fun testGetHeadlines_Success() = runBlocking {
        assert(Api.getHeadlines().isSuccessful)

    }

}

class ArticleUnitTest {

    @Test
    fun testDataOfArticles_Success() = runBlocking {
        val expectedArticles = listOf(
            Article(
                key = 1,
                author = "Author1",
                content = "Content1",
                description = "Description1",
                publishedAt = "2024-11-26",
                source = Source(id = "1", name = "Source1"),
                title = "Title1",
                url = "https://url1.com",
                urlToImage = "https://image1.com"
            ),
            Article(
                key = 2,
                author = "Author2",
                content = "Content2",
                description = "Description2",
                publishedAt = "2024-11-26",
                source = Source(id = "2", name = "Source2"),
                title = "Title2",
                url = "https://url2.com",
                urlToImage = "https://image2.com"
            ),
            Article(
                key = 3,
                author = "Author3",
                content = "Content3",
                description = "Description3",
                publishedAt = "2024-11-26",
                source = Source(id = "3", name = "Source3"),
                title = "Title3",
                url = "https://url3.com",
                urlToImage = "https://image3.com"
            ),
            Article(
                key = 4,
                author = "Author4",
                content = "Content4",
                description = "Description4",
                publishedAt = "2024-11-26",
                source = Source(id = "4", name = "Source4"),
                title = "Title4",
                url = "https://url4.com",
                urlToImage = "https://image4.com"
            ),
            Article(
                key = 5,
                author = "Author5",
                content = "Content5",
                description = "Description5",
                publishedAt = "2024-11-26",
                source = Source(id = "5", name = "Source5"),
                title = "Title5",
                url = "https://url5.com",
                urlToImage = "https://image5.com"
            )
        )

        val actualArticles = listOf(
            Article(
                key = 1,
                author = "Author1",
                content = "Content1",
                description = "Description1",
                publishedAt = "2024-11-26",
                source = Source(id = "1", name = "Source1"),
                title = "Title1",
                url = "https://url1.com",
                urlToImage = "https://image1.com"
            ),
            Article(
                key = 2,
                author = "Author2",
                content = "Content2",
                description = "Description2",
                publishedAt = "2024-11-26",
                source = Source(id = "2", name = "Source2"),
                title = "Title2",
                url = "https://url2.com",
                urlToImage = "https://image2.com"
            ),
            Article(
                key = 3,
                author = "Author3",
                content = "Content3",
                description = "Description3",
                publishedAt = "2024-11-26",
                source = Source(id = "3", name = "Source3"),
                title = "Title3",
                url = "https://url3.com",
                urlToImage = "https://image3.com"
            ),
            Article(
                key = 4,
                author = "Author4",
                content = "Content4",
                description = "Description4",
                publishedAt = "2024-11-26",
                source = Source(id = "4", name = "Source4"),
                title = "Title4",
                url = "https://url4.com",
                urlToImage = "https://image4.com"
            ),Article(
                key = 5,
                author = "Author5",
                content = "Content5",
                description = "Description5",
                publishedAt = "2024-11-26",
                source = Source(id = "5", name = "Source5"),
                title = "Title5",
                url = "https://url5.com",
                urlToImage = "https://image5.com"
            )
        )

        assertEquals(expectedArticles.size, actualArticles.size)


        for (i in expectedArticles.indices) {
            val expected = expectedArticles[i]
            val actual = actualArticles[i]

            assertEquals("Key mismatch at index $i", expected.key, actual.key)
            assertEquals("Author mismatch at index $i", expected.author, actual.author)
            assertEquals("Content mismatch at index $i", expected.content, actual.content)
            assertEquals("Description mismatch at index $i", expected.description, actual.description)
            assertEquals("PublishedAt mismatch at index $i", expected.publishedAt, actual.publishedAt)
            assertEquals("Source ID mismatch at index $i", expected.source.id, actual.source.id)
            assertEquals("Source Name mismatch at index $i", expected.source.name, actual.source.name)
            assertEquals("Title mismatch at index $i", expected.title, actual.title)
            assertEquals("URL mismatch at index $i", expected.url, actual.url)
            assertEquals("URLToImage mismatch at index $i", expected.urlToImage, actual.urlToImage)
        }
    }
}





//@RunWith(AndroidJUnit4::class)
//class ExampleInstrumentedTest {
//    @Test
//    fun useAppContext() {
//        // Context of the app under test.
//        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
//        assertEquals("com.example.kursnewsapp", appContext.packageName)
//    }
//}