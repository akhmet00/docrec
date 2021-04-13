## API documentation for document ocr
### Table of contents
- [OCR ID Card (new type)](#ocr-id-card-new-type)
- [OCR ID Card (old type)](#ocr-id-card-old-type)



#### OCR ID card (new type)
### Description
Provides main information  
```
POST /api/v1/read/identityCard
Request Param: "file" multipart file
```


#### Sample response
```
200 OK
{
    "firstName": "НУРЖАН", //String
    "lastName": "РАЙХАНОВ", //String
    "fathersName": "МАРАТОВИЧ", //String
    "iin": "000126501343", //String
    "birthday": "26.01.20002" //String
}
```



#### OCR ID card (old type)
### Description
Provides main information
```
POST /api/v1/read/identityCard/old
Request Param: "file" multipart file 
```


#### Sample response
```
200 OK
{
    "firstName": "НУРЖАН", //String
    "lastName": "РАЙХАНОВ", //String
    "fathersName": "МАРАТОВИЧ", //String
    "iin": "000126501343", //String 
    "birthday": "26.01.20002" //String
}
```


