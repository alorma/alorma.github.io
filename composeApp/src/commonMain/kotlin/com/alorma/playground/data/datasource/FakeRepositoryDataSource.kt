package com.alorma.playground.data.datasource

import com.alorma.playground.domain.datasource.RepositoryDataSource
import com.alorma.playground.domain.model.Repository

class FakeRepositoryDataSource : RepositoryDataSource {
    override suspend fun getRepositories(username: String): List<Repository> {
        return listOf(
            Repository(
                name = "compose-ui-library",
                fullName = "$username/compose-ui-library",
                htmlUrl = "https://github.com/$username/compose-ui-library",
                pagesUrl = "https://$username.github.io/compose-ui-library",
                description = "A beautiful Compose Multiplatform UI library"
            ),
            Repository(
                name = "kotlin-playground",
                fullName = "$username/kotlin-playground",
                htmlUrl = "https://github.com/$username/kotlin-playground",
                pagesUrl = "https://$username.github.io/kotlin-playground",
                description = "Playground for Kotlin experiments"
            ),
            Repository(
                name = "wasm-demo",
                fullName = "$username/wasm-demo",
                htmlUrl = "https://github.com/$username/wasm-demo",
                pagesUrl = "https://$username.github.io/wasm-demo",
                description = "WebAssembly demo project"
            ),
            Repository(
                name = "private-repo",
                fullName = "$username/private-repo",
                htmlUrl = "https://github.com/$username/private-repo",
                pagesUrl = "https://example.com/private",
                description = "This one should be filtered out"
            )
        )
    }
}
