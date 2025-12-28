package com.alorma.playground.data.model

import com.alorma.playground.domain.model.Repository
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GitHubRepoResponse(
  val name: String,
  @SerialName("full_name")
  val fullName: String,
  @SerialName("html_url")
  val htmlUrl: String,
  val homepage: String? = null,
  val description: String? = null
)

fun GitHubRepoResponse.toDomain(): Repository {
  return Repository(
    name = name,
    fullName = fullName,
    htmlUrl = htmlUrl,
    pagesUrl = homepage ?: "",
    description = description
  )
}
