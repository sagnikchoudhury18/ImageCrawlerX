# ImageCrawlerX
This is a Spring Boot application with a GraphQL endpoint that performs a multi-threaded BFS (Breadth-First Search) crawl to retrieve all images from a given website URL up to a specified depth.


#GraphQL request

url: http://localhost:8080/graphql
type: POST
raw body: {
  "query": "mutation { crawlWebsite(url: \"https://shutterstock.com\", name: \"ShutterStock\", levels: 3) { id name url levels images { id url } } }"
}
set Content-Type as  application/json in request headers


#Rest call

http://localhost:8080/api/crawl
type: POST
body: {
  "url": "https://shutterstock.com",
  "name": "ShutterStock",
  "levels": 3
}
set Content-Type as  application/json in request headers
