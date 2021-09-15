# CustomerDataStore
Customer data operations based on the file type specified
The microservice accepts the customer json data and also the file type (csv or xml).
3 endpoints for data store,update and fetch is exposed.
The microservice validates the input data and encrypts it and passes it to the second microservice where data is decrypted and saved a in the file format specified

