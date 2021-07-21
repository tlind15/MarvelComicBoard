# MarvelComicBoard

## Libraries Used
- Kotlinx Coroutines (asynchronous/concurrent programming)
- Android Viewmodel (android lifecycle component for MVVM architecture)
- Android Lifecycle Runtime (provides lifecycle owner coroutine scope)
- Hilt (dependency injection framework)
- Glide (image loading of comic images)
- Retrofit (HTTP network requests to Marvel API)
- Gson (for parsing REST responses as JSON)
- JUnit (unit testing framework)
- MockK (mocking framework for tests)
- FFmpegMediaMetadataRetriever-native (work around for bug in mockk for instrumented tests)
- Espresso (UI test framework)

## Adding Developer Keys
To add developer keys perform the following steps:
1. Copy the `keys.sample.properties` file to an **UNTRACKED** file named `keys.properties`
2. In the newly created file set the `PUBLIC_KEY` property to the value of your public key
3. In the newly created file set the `PRIVATE_KEY` property to the value of your private key

> Note: Make sure the values in the `keys.properties` file are inside of double quotes and
>make sure there is no sapce between the `=` and the value
