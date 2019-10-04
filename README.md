# Groovy Script to retrive all Tags for an image in a Docker Registry

This is a little workaround to retrive all tags for a specific Docker image.  
I noticed that the Docker api to retrive tags for an image is limited to 100 result.  
But if I need all of this? I saved it in a collection for general purpose.  
As [Docker reference](https://docs.docker.com/registry/spec/api/#listing-image-tags) says the tags are paginated, and, if there are more tags to retrive in the HTTP Header you will have a row with *Link* reference, so I match that with a simple regex to calculate how many tags are there.
After that I used the REST API response: *GET /v2/<name>/tags/list?n=<integer>* to get them all.