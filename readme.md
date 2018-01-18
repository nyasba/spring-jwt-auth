# api-auth

Spring APIの認証をJWT(JsonWebToken)で実装したサンプルです

#### 起動

```
./gradlew clean bootRun
```

#### ログイン

```
curl -v -X POST -d "{ \"loginId\" : \"nyasba\", \"pass\" : \"password\"}" -H "accept: application/json" -H "Content-Type: application/json" "http://localhost:8080/user/login" | jq .

< HTTP/1.1 200
< Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJueWFzYmEiLCJleHAiOjE1MTYyNzE0Njd9.LXCxgcrDW-gtwSnAus3nVAMBrhQitAxmDn4k7dMZ9u82PLqnw467xjpwk67oz93PNy80cNxfBg0LuVbehIih3w

```

#### tokenを使ったAPI実行

成功

```
curl -X GET -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJueWFzYmEiLCJleHAiOjE1MTYyNzE0Njd9.LXCxgcrDW-gtwSnAus3nVAMBrhQitAxmDn4k7dMZ9u82PLqnw467xjpwk67oz93PNy80cNxfBg0LuVbehIih3w" "http://localhost:8080/private" 
this is private
```

失敗

```
curl -X GET "http://localhost:8080/private"
{"timestamp":1516242884788,"status":403,"error":"Forbidden","message":"Access Denied","path":"/private"}

curl -X GET -H "Authorization: Bearer dummy" "http://localhost:8080/private"
{"timestamp":1516242871156,"status":500,"error":"Internal Server Error","exception":"io.jsonwebtoken.MalformedJwtException","message":"JWT strings must contain exactly 2 period characters. Found: 0","path":"/private"}
```
