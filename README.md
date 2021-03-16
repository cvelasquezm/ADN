# ADN

##Run Instructions

```mvn clean install spring-boot:run```

host: https://adn-svc-dot-adn-svc.ue.r.appspot.com

##Statistics Endpoint

[GET] `/stats` HTTP/1.1
> Body: no apply <br>
> Expected Response:
```
{
      "ratio": 0.0,
      "count_mutant_dna": 0,
      "count_human_dna": 0
}
```

##Analysis Endpoint

[POST] `/mutant` HTTP/1.1
> Body: 
```
{
    "dna":["String","String","String","String","String","String"]
}
```
> Sample:
```
{
    "dna":["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"]
}
```
> Expected Response
>> 403 Forbidden if Human DNA <br>
>> 200 OK if Mutant DNA