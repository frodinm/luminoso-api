Api-authorization

- Adds api key introspection endpoint within the SecurityFilterChain.
- Auto configuration for resource servers in order to validate api keys.

-- NimbusJwtDecoder has a RestOperations (RestTemplate) instance which needs to be replaced in order to work with https.
- https://docs.spring.io/spring-security/site/docs/current/reference/html5/#oauth2resourceserver-jwt-decoder-dsl

issue : Certificate for <localhost> doesn't match any of the subject alternative names: []
solution : Add -ext "SAN:c=DNS:localhost,IP:127.0.0.1" 

ex:  keytool -genkeypair -keyalg RSA -keysize 2048 -alias jwt -dname "CN=Fabrizio Rodin-Miron,OU=Fabrizio Rodin-Miron,O=Quebec,C=CA" -ext "SAN:c=DNS:localhost,IP:127.0.0.1" -validity 3650 -keystore analytics.jks -storepass password -keypass password -deststoretype pkcs12
